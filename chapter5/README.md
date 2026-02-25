# Chapter 5: Multi-Agent Architecture with Spring AI

> **Equivalent of:** [Python Strands A2A Integration](https://github.com/aws-samples/sample-once-upon-agentic-ai/tree/solution-v3/5_a2a_integration/agents)
> **Pattern:** Agent-to-Agent (A2A) communication via REST + MCP

## Architecture

```
                    ┌──────────────────────────┐
                    │   GameMasterOrchestrator  │
                    │       (port 8009)         │
                    │                           │
                    │  POST /inquire            │
                    │  GET  /health             │
                    │  GET  /user/{name}        │
                    └─────┬──────┬──────┬───────┘
                          │      │      │
              ┌───────────┘      │      └───────────┐
              │ HTTP POST /ask   │ MCP /mcp          │ HTTP POST /ask
              ▼                  ▼                    ▼
 ┌─────────────────┐  ┌──────────────────┐  ┌─────────────────┐
 │   Rules Agent   │  │  Dice MCP Server │  │ Character Agent │
 │   (port 8000)   │  │   (port 8080)    │  │   (port 8001)   │
 │                 │  │   (from ch.4)    │  │                 │
 │ queryDndRules   │  │   rollDice       │  │ createCharacter │
 │                 │  │                  │  │ findByName      │
 │                 │  │                  │  │ listAll         │
 └─────────────────┘  └──────────────────┘  └─────────────────┘
```

## Python ↔ Java Mapping

| Python (Strands)                  | Java (Spring AI)                        |
|-----------------------------------|-----------------------------------------|
| `A2AServer(agent, port=8001)`     | Spring Boot `@RestController` + `/ask`  |
| `A2AClientToolProvider(urls)`     | `@Tool` methods with `HttpClient` calls |
| `@tool` decorator                 | `@Tool` annotation                      |
| `Agent(tools, system_prompt)`     | `ChatClient.builder().defaultSystem()`  |
| `TinyDB('characters.json')`      | `CharacterStore` + Jackson ObjectMapper |
| ChromaDB `PersistentClient`       | `SimpleVectorStore` + Bedrock Titan Embeddings |
| `MCPClient` (Strands)             | `McpClient.sync()` + `SyncMcpToolCallbackProvider` |
| FastAPI `@app.post("/inquire")`   | Spring `@PostMapping("/inquire")`       |
| `response.structured_output`      | `ChatClient...call().content()`         |

## Prerequisites

- **Java 25+** (install via SDKMAN: `sdk install java 25.ea-open`)
- **JBang** (install: `curl -Ls https://sh.jbang.dev | bash -s - app setup`)
- **AWS credentials** configured (`aws configure` or environment variables)
- **AWS Bedrock** access to Claude Haiku + Titan Embed Text V2 in `eu-central-1`
- **D&D Basic Rules PDF** (`DnD_BasicRules_2018.pdf`) placed in the chapter5 directory

## Step 0: Build the Knowledge Base

Before starting the agents, ingest the D&D rules PDF into a vector store:

```bash
jbang CreateKnowledgeBase.java
# Reads DnD_BasicRules_2018.pdf → creates dnd_knowledge_base.json
# Uses Bedrock Titan Embed Text V2 for embeddings
```

This is equivalent to the Python `create_knowledge_base.py` that builds the ChromaDB index.

## Running the Agents

You need **4 terminals** — one for each service:

### Terminal 1: Dice MCP Server (from chapter4)

```bash
cd ../chapter4
jbang DiceRollMcpServer.java
# Starts on port 8080 — provides rollDice via MCP
```

### Terminal 2: Rules Agent

```bash
jbang RulesAgent.java
# Starts on port 8000 — provides D&D rules lookup
```

### Terminal 3: Character Agent

```bash
jbang CharacterAgent.java
# Starts on port 8001 — provides character CRUD
```

### Terminal 4: Game Master Orchestrator

```bash
jbang GameMasterOrchestrator.java
# Starts on port 8009 — orchestrates all agents
```

## Testing the System

### Health checks

```bash
curl http://localhost:8000/health
curl http://localhost:8001/health
curl http://localhost:8009/health
```

### Ask the Rules Agent directly

```bash
curl -X POST http://localhost:8000/ask \
  -H "Content-Type: application/json" \
  -d '{"question": "How do attack rolls work?"}'
```

### Ask the Character Agent directly

```bash
curl -X POST http://localhost:8001/ask \
  -H "Content-Type: application/json" \
  -d '{"question": "List all characters"}'
```

### Create a character via the Character Agent

```bash
curl -X POST http://localhost:8001/ask \
  -H "Content-Type: application/json" \
  -d '{"question": "Create a female Elf Wizard named Lyria with rolled ability scores"}'
```

### Ask the Orchestrator (delegates to agents)

```bash
curl -X POST http://localhost:8009/inquire \
  -H "Content-Type: application/json" \
  -d '{"question": "Roll a d20 for an attack roll and tell me the rules for critical hits"}'
```

```bash
curl -X POST http://localhost:8009/inquire \
  -H "Content-Type: application/json" \
  -d '{"question": "Create a new character: a male Dwarf Fighter named Thorin"}'
```

```bash
curl -X POST http://localhost:8009/inquire \
  -H "Content-Type: application/json" \
  -d '{"question": "Find the character named Gypsy and explain the rules for Bard spellcasting"}'
```

### Look up a character by name

```bash
curl http://localhost:8009/user/Gypsy
```

## How It Works

### Agent-to-Agent Communication

Each agent is a standalone Spring Boot application with its own `ChatClient` (backed by Bedrock Claude Haiku) and domain-specific `@Tool` methods. The orchestrator's LLM sees the remote agents as tools it can call:

```java
// The orchestrator's tools — each one calls a remote agent via HTTP
@Tool(description = "Send a message to the Rules Agent...")
String askRulesAgent(String question) {
    return callAgent("http://127.0.0.1:8000/ask", question);
}

@Tool(description = "Send a message to the Character Agent...")
String askCharacterAgent(String question) {
    return callAgent("http://127.0.0.1:8001/ask", question);
}
```

When the orchestrator receives a question like *"Create a character and roll for initiative"*, Claude decides to:
1. Call `askCharacterAgent` → HTTP POST to port 8001 → Character Agent's ChatClient uses `createCharacter` tool
2. Call `rollDice` → MCP call to port 8080 → Dice Server rolls the dice
3. Call `askRulesAgent` → HTTP POST to port 8000 → Rules Agent's ChatClient looks up initiative rules
4. Synthesize everything into a Game Master narration

### MCP Integration

The orchestrator reuses the Dice MCP Server from chapter4. The MCP tools are discovered at startup and merged with the agent tools:

```java
var response = chatClient.prompt()
        .user(question)
        .tools(agentTools)           // A2A tools (HTTP to agents)
        .toolCallbacks(mcpTools)     // MCP tools (dice rolling)
        .call()
        .content();
```

## Exercises

### Exercise 1: Add a Combat Agent (Easy)
Create a new `CombatAgent.java` on port 8002 that manages combat encounters. Give it tools for tracking initiative order and HP. Register it in the orchestrator.

### Exercise 2: Add Conversation Memory (Medium)
Add `MessageChatMemoryAdvisor` to the orchestrator's ChatClient so it remembers previous interactions within a session.

### Exercise 3: Structured Output (Medium)
Add a `StoryOutput` record to the orchestrator's response (matching the Python `StoryOutput` Pydantic model) using Spring AI's structured output support with `BeanOutputConverter`.

### Exercise 4: Swap Vector Store Backend (Hard)
Replace the file-based `SimpleVectorStore` with a production vector database like PGVector or OpenSearch, using Spring AI's `VectorStore` abstraction (zero changes to the Rules Agent's tool code).
