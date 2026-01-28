# Component Dependencies

## Overview
Each chapter is a separate Spring Boot application with no dependencies on other chapters. Dependencies exist only within each chapter between its internal components.

---

## Dependency Matrix

### Chapter 0: Prerequisites and Setup
```
Chapter0Application
    └── Chapter0Controller
            └── BedrockHealthService
                    └── BedrockConfigProperties
```

**Dependencies**:
- Chapter0Controller → BedrockHealthService
- BedrockHealthService → BedrockConfigProperties

**Communication Pattern**: REST → Service → Configuration

---

### Chapter 1: Agent Basics
```
Chapter1Application
    ├── Chapter1Controller
    │       └── AgentService
    │               ├── ChatClient (Spring AI)
    │               └── ConversationMemory
    └── BedrockChatConfig
            └── BedrockConfigProperties
```

**Dependencies**:
- Chapter1Controller → AgentService
- AgentService → ChatClient, ConversationMemory
- BedrockChatConfig → BedrockConfigProperties

**Communication Pattern**: REST → Service → ChatClient + Memory

---

### Chapter 2: Built-in Tools
```
Chapter2Application
    ├── Chapter2Controller
    │       └── AgentService
    │               ├── ChatClient (Spring AI)
    │               ├── ConversationMemory
    │               └── CharacterStatsFunction
    └── BedrockChatConfig
            ├── BedrockConfigProperties
            └── CharacterStatsFunction (registered)
```

**Dependencies**:
- Chapter2Controller → AgentService
- AgentService → ChatClient, ConversationMemory
- ChatClient → CharacterStatsFunction (via function registration)
- BedrockChatConfig → BedrockConfigProperties, CharacterStatsFunction

**Communication Pattern**: REST → Service → ChatClient → Function

---

### Chapter 3: Custom Tools
```
Chapter3Application
    ├── Chapter3Controller
    │       └── AgentService
    │               ├── ChatClient (Spring AI)
    │               ├── ConversationMemory
    │               └── DiceRollingTool
    │                       └── DiceNotationParser
    └── BedrockChatConfig
            ├── BedrockConfigProperties
            └── DiceRollingTool (registered)
```

**Dependencies**:
- Chapter3Controller → AgentService
- AgentService → ChatClient, ConversationMemory
- ChatClient → DiceRollingTool (via function registration)
- DiceRollingTool → DiceNotationParser
- BedrockChatConfig → BedrockConfigProperties, DiceRollingTool

**Communication Pattern**: REST → Service → ChatClient → Custom Tool → Parser

---

### Chapter 4: MCP Integration
```
Chapter4Application
    ├── Chapter4Controller
    │       └── AgentService
    │               ├── ChatClient (Spring AI)
    │               ├── ConversationMemory
    │               └── SpellDatabaseTool
    │                       └── McpService
    │                               └── MCP Server (external)
    ├── BedrockChatConfig
    │       ├── BedrockConfigProperties
    │       └── SpellDatabaseTool (registered)
    └── McpClientConfig
            └── McpService
```

**Dependencies**:
- Chapter4Controller → AgentService
- AgentService → ChatClient, ConversationMemory
- ChatClient → SpellDatabaseTool (via function registration)
- SpellDatabaseTool → McpService
- McpService → External MCP Server
- BedrockChatConfig → BedrockConfigProperties, SpellDatabaseTool
- McpClientConfig → McpService

**Communication Pattern**: REST → Service → ChatClient → MCP Tool → MCP Service → External Server

---

### Chapter 5: Agent-to-Agent Communication
```
Chapter5Application
    ├── Chapter5Controller
    │       └── GameMasterAgent (initiator)
    │               ├── A2AClient
    │               ├── A2AServer
    │               ├── ChatClient (Spring AI)
    │               └── AgentContext
    ├── A2AServer (JSON-RPC 2.0)
    │       ├── A2AMessageHandler
    │       ├── A2ATaskExecutor
    │       └── AgentRegistry
    ├── A2AClient (JSON-RPC 2.0)
    │       └── HTTP client for A2A requests
    ├── AgentRegistry
    │       └── Agent cards storage
    ├── GameMasterAgent
    │       ├── AgentCard (capabilities: game_master, scenario_generation)
    │       ├── A2AClient
    │       ├── A2ATaskExecutor
    │       ├── ChatClient (Spring AI)
    │       └── AgentContext
    ├── PlayerAgent
    │       ├── AgentCard (capabilities: player, action_decision)
    │       ├── A2AClient
    │       ├── A2ATaskExecutor
    │       ├── ChatClient (Spring AI)
    │       └── AgentContext
    ├── RulesExpertAgent
    │       ├── AgentCard (capabilities: rules_expert, rule_clarification)
    │       ├── A2AClient
    │       ├── A2ATaskExecutor
    │       ├── ChatClient (Spring AI)
    │       └── AgentContext
    ├── StorytellerAgent
    │       ├── AgentCard (capabilities: storyteller, narrative_generation)
    │       ├── A2AClient
    │       ├── A2ATaskExecutor
    │       ├── ChatClient (Spring AI)
    │       └── AgentContext
    └── BedrockChatConfig
            └── BedrockConfigProperties
```

**Dependencies**:
- Chapter5Controller → GameMasterAgent (initiator)
- Each Agent → AgentCard, A2AClient, A2ATaskExecutor, ChatClient, AgentContext
- A2AServer → A2AMessageHandler, A2ATaskExecutor, AgentRegistry
- A2AClient → HTTP client for JSON-RPC 2.0 requests
- AgentRegistry → Agent cards from all agents
- BedrockChatConfig → BedrockConfigProperties

**Communication Pattern**: REST → Initiator Agent → A2A Client (JSON-RPC 2.0) → A2A Server → Target Agent → Task Executor → Response

---

## External Dependencies

### All Chapters
- **Spring Boot 3.x**: Application framework
- **Spring AI**: Agent and ChatClient abstractions
- **AWS SDK for Bedrock**: LLM provider integration
- **Jackson**: JSON serialization
- **Lombok** (optional): Reduce boilerplate

### Chapter 4 Only
- **MCP Client Library**: Model Context Protocol integration

---

## Data Flow Diagrams

### Chapter 0: Health Check Flow
```
User → REST API → Controller → HealthService → Bedrock API
                                      ↓
                                  Response
                                      ↓
User ← REST API ← Controller ← HealthService
```

### Chapter 1: Basic Chat Flow
```
User → REST API → Controller → AgentService → ChatClient → Bedrock
                                      ↓              ↓
                              ConversationMemory    Response
                                      ↓              ↓
User ← REST API ← Controller ← AgentService ← ChatClient
```

### Chapter 2: Function Calling Flow
```
User → REST API → Controller → AgentService → ChatClient → Bedrock
                                      ↓              ↓
                              ConversationMemory    Function Call
                                                     ↓
                                            CharacterStatsFunction
                                                     ↓
                                                  Result
                                                     ↓
User ← REST API ← Controller ← AgentService ← ChatClient
```

### Chapter 3: Custom Tool Flow
```
User → REST API → Controller → AgentService → ChatClient → Bedrock
                                      ↓              ↓
                              ConversationMemory    Tool Call
                                                     ↓
                                              DiceRollingTool
                                                     ↓
                                              DiceNotationParser
                                                     ↓
                                                  Result
                                                     ↓
User ← REST API ← Controller ← AgentService ← ChatClient
```

### Chapter 4: MCP Integration Flow
```
User → REST API → Controller → AgentService → ChatClient → Bedrock
                                      ↓              ↓
                              ConversationMemory    MCP Tool Call
                                                     ↓
                                              SpellDatabaseTool
                                                     ↓
                                                 McpService
                                                     ↓
                                              MCP Server (external)
                                                     ↓
                                                  Result
                                                     ↓
User ← REST API ← Controller ← AgentService ← ChatClient
```

### Chapter 5: Multi-Agent A2A Protocol Flow
```
User → REST API → Controller → GameMasterAgent (initiator)
                                      ↓
                              A2AClient (JSON-RPC 2.0)
                                      ↓
                              A2AServer (JSON-RPC 2.0)
                                      ↓
                    ┌─────────────────┼─────────────────┐
                    ↓                 ↓                 ↓
              PlayerAgent      RulesExpertAgent  StorytellerAgent
                    ↓                 ↓                 ↓
            A2ATaskExecutor   A2ATaskExecutor   A2ATaskExecutor
                    ↓                 ↓                 ↓
               ChatClient        ChatClient        ChatClient
                    ↓                 ↓                 ↓
                 Bedrock           Bedrock           Bedrock
                    ↓                 ↓                 ↓
              AgentContext      AgentContext      AgentContext
                    ↓                 ↓                 ↓
            A2ATaskResult     A2ATaskResult     A2ATaskResult
                    ↓                 ↓                 ↓
                    └─────────────────┼─────────────────┘
                                      ↓
                              A2AServer (JSON-RPC 2.0)
                                      ↓
                              A2AClient (JSON-RPC 2.0)
                                      ↓
                              GameMasterAgent
                                      ↓
User ← REST API ← Controller ← Coordinated Response
```

---

## Integration Points

### AWS Bedrock Integration
- **All Chapters**: Use AWS SDK to invoke Bedrock models
- **Configuration**: AWS credentials via environment variables
- **Authentication**: IAM-based authentication
- **Models**: Claude, Titan, or other Bedrock-supported models

### Web Application Integration
- **All Chapters**: Expose REST API endpoints
- **Protocol**: HTTP/JSON
- **Endpoints**: `/api/chat`, `/api/game`, etc.
- **CORS**: Configured for web application origin

### MCP Server Integration (Chapter 4 Only)
- **Protocol**: Model Context Protocol
- **Connection**: HTTP or WebSocket
- **Tool Discovery**: Dynamic tool registration
- **Error Handling**: Connection retry and fallback

---

## Dependency Management Principles

1. **No Cross-Chapter Dependencies**: Each chapter is completely independent
2. **Minimal External Dependencies**: Only essential libraries included
3. **Clear Layering**: Controller → Service → Client/Tool pattern
4. **Stateful Services**: Services maintain conversation state in memory
5. **Configuration Injection**: All configuration via Spring dependency injection
