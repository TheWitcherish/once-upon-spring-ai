# Once Upon Spring AI - Development Guide

This guide provides comprehensive information about the "Once Upon Spring AI" workshop project, including project overview, build commands, code style guidelines, and testing instructions.

## Project Overview

**Once Upon Spring AI** is a hands-on workshop series teaching developers how to build AI agents using **Java 25** and **Spring AI 2.0** with **AWS Bedrock**. The workshop uses D&D (Dungeons & Dragons) storytelling as a creative theme to progressively introduce AI agent development concepts.

### Tech Stack

- **Java 25+** - Modern Java with records, unnamed classes, text blocks, markdown doc comments (`///`)
- **JBang** - Build tool — runs `.java` files directly with embedded dependency metadata (`//DEPS`, `//REPOS`, `//SOURCES`)
- **Spring AI 2.0.0-M2** - Spring's AI/ML integration framework
- **Spring Boot 4.0.2** - For Chapters 4-5 (MCP Server, A2A agents)
- **Spring Framework 7.0.3** - Core framework
- **AWS Bedrock** - Cloud-based AI model hosting
- **AWS SDK for Java 2.41.34** - AWS service client libraries
- **Claude Haiku 4.5** (Anthropic) - AI model via Bedrock (`eu.anthropic.claude-haiku-4-5-20251001-v1:0`)
- **Amazon Titan Embed Text V2** - Embedding model for vector store (Chapter 5)
- **spring-ai-agent-utils 0.4.2** - Spring AI Community tools library (Chapter 2: SmartWebFetchTool)
- **mcp-annotations 0.8.0** - Spring AI Community `@McpTool`/`@McpToolParam` annotations (Chapter 4)
- **MCP Java SDK 1.0.0** - Model Context Protocol client/server (Chapters 4-5)
- **A2A Java SDK 0.3.3.Final** - Agent-to-Agent protocol client (Chapter 5)
- **spring-ai-a2a-server-autoconfigure 0.2.0** - A2A server auto-configuration (Chapter 5)
- **Jackson 2.18.4** - JSON serialization for character persistence (Chapter 5)

### Workshop Structure

The project contains 5 progressive chapters, each building upon the previous:

| Chapter | Title | Description | Key Concepts |
|---------|-------|-------------|--------------|
| **Chapter 1** | The Art of Agent Summoning | Create your first AI agent - a Dungeon Master chatbot | ChatClient, System Prompts, AWS Bedrock Integration |
| **Chapter 2** | AI Agent with Built-in Tools | Equip agent with SmartWebFetchTool from `spring-ai-agent-utils` for autonomous web content fetching | Community Tools (`spring-ai-agent-utils`), Tool Registration, Autonomous Tool Use |
| **Chapter 3** | The Adventurer's Arsenal | Build custom tools (dice rolling) for AI agents | Custom Tools, @Tool/@ToolParam, //SOURCES directive, Agentic Loop |
| **Chapter 4** | The Tavern Notice Board | Expose tools as MCP Server + MCP Client over Streamable HTTP | MCP Protocol, `@McpTool`/`@McpToolParam`, `SyncMcpToolCallbackProvider`, Interactive REPL |
| **Chapter 5** | The Council of Agents | Multi-agent architecture with A2A protocol + MCP + RAG | A2A Protocol, `AgentCard`, `DefaultAgentExecutor`, `sendMessage` Tool, Vector Store, Orchestrator Pattern |

### Project Structure

```
once-upon-spring-ai/
├── README.md                    # Main project documentation
├── AGENTS.md                    # This file - development guide
├── LICENSE                      # Project license
├── .gitignore                   # Git ignore rules
├── chapter1/                    # Chapter 1: Simple AI Agent
│   ├── README.md                # Chapter-specific instructions
│   └── DungeonMasterSimple.java # JBang executable (unnamed class)
├── chapter2/                    # Chapter 2: Built-in Tools
│   ├── README.md
│   └── DungeonMasterWithBuiltInTools.java
├── chapter3/                    # Chapter 3: Custom Tools
│   ├── README.md
│   ├── DiceTools.java           # Reusable @Tool class (included via //SOURCES)
│   └── DungeonMasterWithCustomTools.java
├── chapter4/                    # Chapter 4: MCP Server + Client
│   ├── README.md
│   ├── DiceRollMcpServer.java   # Spring Boot MCP server (port 8080) — DiceTools @McpTool class defined inline
│   ├── DungeonMasterMCPClient.java  # Standalone MCP client with interactive REPL (IO.readln loop)
│   └── application.properties   # MCP server configuration (STREAMABLE protocol)
└── chapter5/                    # Chapter 5: Multi-Agent A2A Architecture
    ├── README.md
    ├── agents/
    │   ├── rules/
    │   │   ├── RulesAgent.java          # A2A server — D&D rules lookup (port 8000), includes VectorStoreConfig + RulesAgentConfig
    │   │   └── RulesTools.java          # @Tool: queryDndRules (vector store similarity search, top-3 results)
    │   ├── character/
    │   │   ├── CharacterAgent.java      # A2A server — character CRUD (port 8001), includes CharacterAgentConfig
    │   │   ├── CharacterTools.java      # @Tool: createCharacter, findCharacterByName, listAllCharacters + CharacterStore
    │   │   └── characters.json          # Character database (JSON persistence, auto-created)
    │   └── gamemaster/
    │       ├── GameMasterOrchestrator.java  # Main app + MCP client config + CORS config (port 8009)
    │       ├── GameMasterService.java       # A2A agent card discovery + @Tool sendMessage (JSON-RPC transport)
    │       └── GameMasterController.java    # REST endpoints (/inquire, /health, /user/{name}, /messages)
    ├── utils/
    │   ├── CreateKnowledgeBase.java     # PDF → vector store ingestion script (Titan Embed V2 embeddings)
    │   ├── DnD_BasicRules_2018.pdf      # Source PDF (not committed)
    │   └── dm_knowledge_base.json       # Generated vector store (not committed)
```

### Prerequisites

1. **Java 25 or higher** installed
   ```bash
   java -version  # Should show: java version "25" or higher
   ```

2. **JBang** installed
   ```bash
   curl -Ls https://sh.jbang.dev | bash -s - app setup
   jbang --version  # Verify installation
   ```

3. **AWS Account** with Bedrock access
   - AWS credentials configured via `~/.aws/credentials` or environment variables
   - Bedrock model access enabled for Claude Haiku 4.5 in `eu-central-1`
   - For Chapter 5: Bedrock access to Amazon Titan Embed Text V2

## Build and Run Commands

All chapters use **JBang** — no Maven, no `pom.xml`, no build step. Dependencies are declared as `//DEPS` comments inside each `.java` file.

### Chapter 1 (Simple AI Agent)

```bash
cd chapter1
jbang DungeonMasterSimple.java
```

### Chapter 2 (Built-in Tools)

```bash
cd chapter2
jbang DungeonMasterWithBuiltInTools.java
```

### Chapter 3 (Custom Tools)

```bash
cd chapter3
jbang DungeonMasterWithCustomTools.java
# DiceTools.java is included automatically via //SOURCES directive
```

### Chapter 4 (MCP Server + Client)

```bash
# Terminal 1: Start the MCP Server (Spring Boot on port 8080)
cd chapter4
jbang DiceRollMcpServer.java

# Terminal 2: Start the interactive MCP Client
cd chapter4
jbang DungeonMasterMCPClient.java
# Interactive REPL — type "Roll a d20" or "exit" to quit
```

#### Test MCP Server with Claude Desktop

Configure in `claude_desktop_config.json`:

```json
{
  "mcpServers": {
    "dice-roll": {
      "type": "streamable-http",
      "url": "http://localhost:8080/mcp"
    }
  }
}
```

### Chapter 5 (Multi-Agent A2A Architecture)

Requires **4 terminals** — start sub-agents first, then the orchestrator.

```bash
# Step 0 (one-time): Build the knowledge base
cd chapter5/utils
jbang CreateKnowledgeBase.java

# Terminal 1: Dice MCP Server (reuses chapter4)
cd chapter4
jbang DiceRollMcpServer.java          # port 8080

# Terminal 2: Rules Agent
cd chapter5/agents/rules
jbang RulesAgent.java                 # port 8000, A2A at /a2a

# Terminal 3: Character Agent
cd chapter5/agents/character
jbang CharacterAgent.java             # port 8001, A2A at /a2a

# Terminal 4: Game Master Orchestrator
cd chapter5/agents/gamemaster
jbang GameMasterOrchestrator.java     # port 8009
```

#### Test Chapter 5

```bash
# Health checks
curl http://localhost:8001/a2a/health
curl http://localhost:8009/health

# Rules lookup (delegated to Rules Agent via A2A)
curl -X POST http://localhost:8009/inquire \
  -H "Content-Type: application/json" \
  -d '{"question": "How do attack rolls work?"}'

# Character creation (delegated to Character Agent via A2A)
curl -X POST http://localhost:8009/inquire \
  -H "Content-Type: application/json" \
  -d '{"question": "Create a new character: a male Dwarf Fighter named Thorin"}'

# Multi-agent query (dice MCP + rules A2A + character A2A)
curl -X POST http://localhost:8009/inquire \
  -H "Content-Type: application/json" \
  -d '{"question": "Roll a d20 for an attack roll and tell me the rules for critical hits"}'
```

## Code Style Guidelines

This section defines code style guidelines for building modern Java applications using Java 25 features and Spring framework best practices.

## Java 25 Modern Features

- **Records**: Use `record` for immutable data carriers instead of traditional classes or Lombok
- **Primitive Types in Patterns**: Use pattern matching with primitive types for cleaner code
- **Flexible Constructor Bodies**: Statements before `super()` calls for better initialization
- **Module Import Declarations**: Simplify imports with `import module` syntax
- **Markdown Documentation Comments**: Use `///` for enhanced JavaDoc with Markdown support
- **Scoped Values**: Thread-safe data sharing without ThreadLocal overhead
- **Structured Concurrency**: Manage concurrent tasks as a single unit of work
- **String Templates**: Type-safe string composition (preview feature)
- **var keyword**: While explicit types are preferred, `var` can be used for local variables when the type is obvious from the right-hand side

## Code Formatting

- Indentation: 4 spaces
- Blank Lines: Use to separate logical blocks of code
- Line Length: Maximum 120 characters
- Use IntelliJ IDEA default code style for Java

## Java Style

- Use UTF-8 encoding
- Use descriptive names for classes, methods, and variables
- `var` keyword is used throughout the workshop for local variables where the type is obvious from the right-hand side (e.g., `var bedrockClient = BedrockRuntimeClient.builder()...`)
- All method parameters should be `final`
- All variables should be declared as `final` where possible
- Preference for immutability:
  - Avoid mutations of objects, especially when using for-each loops or Stream API using `forEach()`
- Avoid magic numbers and strings; use constants instead
- Check emptiness and nullness before operations on collections and strings
- Avoid methods using `throws` clause; prefer unchecked exceptions

- Avoid comments
- Comments could be applied for: cron expressions, Regex patterns, TODOs or given/when/then separation in tests
- Use `@Override` annotation when overriding methods
- Avoid `Objects.isNull()` and `Objects.nonNull()` for one or two variables; prefer direct null checks for better performance
- Wrap multiple conditions in a boolean variable for better readability
- Prefer early returns
- Avoid else statements when not necessary and try early returns

## Lombok Annotations

> **Note:** This workshop does **not** use Lombok. All logging uses manual `LoggerFactory.getLogger()` calls, and dependency injection uses constructor injection without `@RequiredArgsConstructor`. The guidelines below apply to production projects using Lombok.

- Use `@RequiredArgsConstructor` from Lombok for dependency injection via constructor
- Use `@Slf4j` from Lombok for logging
- Use `@Builder(setterPrefix = "with")` for complex object creation
- Avoid `@Data` annotation; prefer `@Getter` and `@Setter` for granular control

Note: For immutable data carriers, prefer Java records over Lombok `@Value` or `@Data` annotations.

## Spring Annotations

- **`@Service`**: For business logic classes
- **`@Repository`**: For data access classes that extend JPA repositories or interact with the database
- **`@RestController`**: For web controllers
- **`@Component`**: For generic Spring components
- **`@Configuration`**: For Spring configuration classes
- **`@Autowired`**: Prefer constructor injection for production code and field injection only for tests
- **`@ConfigurationProperties`**: For binding related properties avoid multiple `@Value` annotations. From more than 2 properties, consider using this annotation
- **`@Transactional`**: Only Service classes should be annotated with @Transactional at class level to avoid transaction management in each method
- **`@Validated`**: To enable Bean Validation in method parameters or classes
- **`@PreAuthorize`**: at the controller layer when using Spring Security to enforce method-level security
- Circular dependencies should be avoided. Avoid `@Order` annotation for dependency resolution

## Mappers

Choose either MapStruct or static mappers for your project.

### Option 1: MapStruct

- Use for mapping between DTOs and entities
- Define mapper interfaces with `@Mapper` annotation
- Use `@Mapping` annotation for custom field mappings
- Use `componentModel = "spring"` to allow Spring to manage mapper instances
- Mapper should have as suffix `Mapper` (e.g., `UserMapper`)
- Name mapper methods clearly (e.g., `toDto`, `toEntity`)

Example:

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "email", target = "emailAddress")
    UserDTO toDto(User user);
    
    @Mapping(source = "emailAddress", target = "email")
    User toEntity(UserDTO userDto);
}
```

For testing mappers, use `Mappers.getMapper(UserMapper.class)` to get an instance of the mapper.

### Option 2: Static Mappers

- Define a private constructor to prevent instantiation with `UnsupportedOperationException("This class should never be instantiated")`
- Use static methods for mapping between DTOs and entities
- Name mapper methods clearly (e.g., `toDto`, `toEntity`)

Example:

```java
public class UserMapper {
    private UserMapper() {
        throw new UnsupportedOperationException("This class should never be instantiated");
    }
    
    public static UserDTO toDto(final User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getEmail());
    }
    
    public static User toEntity(final UserDTO userDto) {
        if (userDto == null) {
            return null;
        }
        return new User(userDto.id(), userDto.emailAddress());
    }
}
```

### Option 3: Records (Recommended for DTOs)

For simple data transfer objects, use Java records directly:

```java
// Immutable DTO using record
record UserDTO(Long id, String emailAddress) {}

// Domain entity
record User(Long id, String email) {}
```

## Exception Handling

- Custom Exceptions: Create custom domain exception classes extending `RuntimeException`
- Global Exception Handler: Use `@ControllerAdvice` and `@ExceptionHandler` to handle exceptions globally
- HTTP Status Codes: Map exceptions to appropriate HTTP status codes in REST controllers
- Error Response Structure: Define a consistent error response structure

## Testing Instructions

### General Testing Guidelines

- Use JUnit 5 for unit and integration testing
- Use Mockito for mocking dependencies in unit tests
- Use `@WebMvcTest(ControllerClass.class)` for testing Spring MVC controllers
- Use `@SpringBootTest` for integration tests that require the Spring context
- Use `given/when/then` structure in test methods for clarity
- Method naming could follow snake_case or camelCase convention for test methods (e.g., `get_user_by_id_ok`, `get_user_by_id_not_found_ko`)
- Avoid reflection in tests
- Avoid business logic in tests; focus on behavior verification

### Workshop-Specific Testing

#### Chapters 1-3: Manual Testing

These chapters are designed for interactive learning and manual testing:

1. **Run the application** with `jbang <FileName>.java`
2. **Observe the output** - AI responses should be logged to console
3. **Verify tool calls** - Look for "TOOL CALLED" messages in Chapter 3
4. **Check AWS connectivity** - Ensure Bedrock API calls succeed

Expected behaviors:
- **Chapter 1**: AI responds with D&D narrative
- **Chapter 2**: AI fetches Wikipedia content and extracts information
- **Chapter 3**: AI calls dice rolling tool and incorporates results into narrative

#### Chapter 4: MCP Server + Client Testing

1. **Start the MCP server** (Terminal 1): `cd chapter4 && jbang DiceRollMcpServer.java`
2. **Verify server is running**: Wait for "Started DiceRollMcpServer" message on port 8080
3. **Start the MCP client** (Terminal 2): `cd chapter4 && jbang DungeonMasterMCPClient.java`
4. **Test interactively**: The client starts an interactive REPL — type "Roll a d20" or "Roll 2d6 for damage"
5. **Verify tool calls**: Check server logs for "TOOL CALLED" messages
6. **Exit the REPL**: Type "exit", "quit", or "bye"

#### Chapter 5: Multi-Agent Testing

1. **Build knowledge base** (one-time): `cd chapter5/utils && jbang CreateKnowledgeBase.java`
2. **Start all 4 services** in separate terminals (see Build and Run Commands above)
3. **Health checks**:
   - `curl http://localhost:8000/a2a/health` (Rules Agent — via Spring Boot actuator)
   - `curl http://localhost:8001/a2a/health` (Character Agent — custom `@GetMapping`)
   - `curl http://localhost:8009/health` (Orchestrator — custom `@GetMapping`)
4. **Test via REST API**: Send POST requests to `http://localhost:8009/inquire`
5. **Verify A2A delegation**: Check agent logs for `sendMessage` tool calls and A2A task responses
6. **Test character persistence**: Characters saved in `chapter5/agents/character/characters.json`
7. **Check conversation history**: `curl http://localhost:8009/messages`
8. **Lookup character directly**: `curl http://localhost:8009/user/Thorin`

### Troubleshooting

#### AWS Credentials Issues
```bash
aws configure list
aws bedrock list-foundation-models --region eu-central-1
```

#### Port Conflicts (Chapters 4-5)
Default ports: MCP Server (8080), Rules Agent (8000), Character Agent (8001), Orchestrator (8009)
- Chapter 4: Edit `application.properties` to change `server.port`
- Chapter 5: Ports are set via `System.setProperty("server.port", ...)` in each agent's `main()` method

#### Tool Not Being Called (Chapter 3)
- Verify system prompt instructs AI to use tools
- Check tool description is clear and relevant
- Ensure user message requires tool usage (e.g., "attack the goblin" should trigger dice roll)

#### Chapter 5 Agent Not Discovered
- Startup order matters: start sub-agents (Rules, Character) before the orchestrator
- If an agent is down at orchestrator startup, it logs an error but still starts (that agent unavailable)
- MCP Dice Server is optional — if unreachable, dice rolling is disabled (returns empty `ToolCallback[]`)
- Agent card discovery URL format: `http://localhost:{port}/a2a/.well-known/agent-card.json`
- The orchestrator reads `remote.agents.urls` property (set via `System.setProperty` in `main()`)

## Logging

- In this workshop: Use `private static final Logger log = LoggerFactory.getLogger(ClassName.class)` or `LoggerFactory.getLogger("LoggerName")`
- In production projects: Use `@Slf4j` annotation from Lombok for logging to avoid boilerplate code with Logger instances
- Log at appropriate levels: `DEBUG`, `INFO`, `WARN`, `ERROR`
- Include contextual information in logs (e.g., request IDs, user IDs)
- Avoid logging sensitive information
- Use structured logging for better log management
- Format log messages with placeholders (e.g., `{}`) instead of string concatenation
- Logging info template: `log.info("[MicroserviceName/ModuleName] - API-CALL/METHOD/ACTION: response: {}, userId: {}", body, userId);`
- Logging error template: `log.error("[MicroserviceName/ModuleName] - API-CALL/METHOD/ACTION: errorMessage: {}, userId: {}", errorMessage, userId);`

## Spring AI Integration

When building AI-powered applications with Spring AI:

### For Embedded Tools (Chapters 1-3)

- Use `@Tool` annotation to mark methods as callable by AI agents
- Define tool input/output using Java records for immutability
- Register tools with `ChatClient.builder().defaultTools(toolInstance)`
- Provide clear tool descriptions to guide AI decision-making
- Use system prompts to instruct AI when to use tools

Example Custom Tool (Chapter 3 — separate `DiceTools.java` file included via `//SOURCES`):

```java
class DiceTools {

    record DiceRollResponse(int[] rolls, int total, String description) {}

    @Tool(description = "Roll dice for D&D game mechanics. Use this for attack rolls, damage, ability checks, or saving throws.")
    DiceRollResponse rollDice(
        @ToolParam(description = "Number of faces on the dice (e.g. 6, 20)", required = true) int faces,
        @ToolParam(description = "Number of dice to roll (e.g. 1, 3)", required = true) int count) {
        // Implementation — returns DiceRollResponse record
    }
}

// Register with ChatClient at call time
var agent = ChatClient.builder(chatModel)
    .defaultSystem("""
        You are Lady Luck, the mystical keeper of dice and fortune in D&D adventures.
        ...""")
    .build();

var response = agent.prompt()
    .user(playerMessage)
    .tools(new DiceTools())  // registered per-prompt, not as defaultTools
    .call()
    .content();
```

### For MCP Servers (Chapter 4)

- Use `@McpTool` to expose methods as MCP (Model Context Protocol) tools
- Use `@McpToolParam` to describe individual parameters with clear descriptions
- Annotate tool classes with `@Component` for auto-discovery by Spring Boot
- Configure MCP server properties in `application.properties`:
  - `spring.ai.mcp.server.name` - Server name (e.g., `dice-mcp-server`)
  - `spring.ai.mcp.server.version` - Server version (e.g., `1.0.0`)
  - `spring.ai.mcp.server.protocol` - Transport protocol (`STREAMABLE` — recommended)
  - `spring.ai.mcp.server.instructions` - Human-readable description of the server
  - `spring.ai.mcp.server.capabilities.prompt` - Enable prompt templates capability

Example MCP Tool (Chapter 4 — defined inline in `DiceRollMcpServer.java`):

```java
@Component
class DiceTools {

    record DiceRollResponse(int[] rolls, int total, String description) {}

    @McpTool(description = "Roll dice for D&D game mechanics. Use this for attack rolls, damage, ability checks, or saving throws.")
    DiceRollResponse rollDice(
            @McpToolParam(description = "Number of faces on the dice (e.g. 6, 20)", required = true) int faces,
            @McpToolParam(description = "Number of dice to roll (e.g. 1, 3)", required = true) int count) {
        // Implementation — returns DiceRollResponse record
    }
}
```

### Key Differences: @Tool vs @McpTool

| Aspect | @Tool (Chapters 3, 5) | @McpTool (Chapter 4) |
|--------|----------------------|----------------------|
| Scope | Embedded in application | Exposed as network service |
| Parameters | Individual `@ToolParam` per parameter | Individual `@McpToolParam` per parameter |
| Discovery | Manual registration via `.tools()` or `.defaultTools()` | Auto-discovered by Spring Boot `@Component` scan |
| Protocol | Internal Spring AI | Standard MCP protocol over Streamable HTTP |
| Clients | Only your ChatClient | Any MCP-compatible client (Claude Desktop, other agents) |
| Use Case | Single application or A2A sub-agents | Shared tools across multiple clients |
| Package | `org.springframework.ai.tool.annotation` | `org.springaicommunity.mcp.annotation` |

### AWS Bedrock Configuration

All chapters use AWS Bedrock with Claude Haiku 4.5:

```java
// Create Bedrock client
var bedrockClient = BedrockRuntimeClient.builder()
    .region(Region.EU_CENTRAL_1)
    .credentialsProvider(DefaultCredentialsProvider.builder().build())
    .build();

// Configure model
var modelId = "eu.anthropic.claude-haiku-4-5-20251001-v1:0";
var options = BedrockChatOptions.builder()
    .model(modelId)
    .build();

// Create ChatModel
var chatModel = BedrockProxyChatModel.builder()
    .bedrockRuntimeClient(bedrockClient)
    .defaultOptions(options)
    .build();
```

### A2A Protocol Integration (Chapter 5)

Chapter 5 uses the Agent-to-Agent (A2A) protocol for multi-agent communication. Each sub-agent is a Spring Boot app that publishes an **agent card** and accepts tasks via JSON-RPC.

#### Sub-Agent Pattern (Rules Agent, Character Agent)

Each A2A sub-agent follows this pattern:

```java
// 1. Spring Boot app with A2A server auto-configuration
@SpringBootApplication
public class RulesAgent {
    public static void main(final String[] args) {
        System.setProperty("server.port", "8000");
        System.setProperty("server.servlet.context-path", "/a2a");
        System.setProperty("spring.ai.a2a.server.enabled", "true");
        SpringApplication.run(RulesAgent.class, args);
    }

    // 2. Agent card — identity, skills, protocol version
    @Bean
    AgentCard agentCard(...) {
        return new AgentCard.Builder()
                .name("Rules Agent")
                .description("...")
                .url("http://localhost:8000/a2a/")
                .skills(List.of(...))
                .protocolVersion("0.3.0")
                .build();
    }

    // 3. Agent executor — ChatClient with tools, handles incoming A2A tasks
    @Bean
    AgentExecutor agentExecutor(BedrockProxyChatModel chatModel, RulesTools rulesTools) {
        var chatClient = ChatClient.builder(chatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultTools(rulesTools)
                .build();

        return new DefaultAgentExecutor(chatClient, (chat, requestContext) -> {
            String userMessage = DefaultAgentExecutor.extractTextFromMessage(requestContext.getMessage());
            return chat.prompt().user(userMessage).call().content();
        });
    }
}
```

#### Orchestrator Pattern (Game Master)

The orchestrator combines A2A tools (via `sendMessage`) and MCP tools (via `SyncMcpToolCallbackProvider`):

```java
// At call time, both A2A and MCP tools are provided to the ChatClient
var content = chatClient.prompt()
        .user(request.question())
        .tools(remoteAgentService)      // A2A tools (sendMessage to agents)
        .toolCallbacks(mcpTools)        // MCP tools (dice rolling)
        .call()
        .content();
```

#### Key A2A Components

| Component | Implementation |
|-----------|---------------|
| Agent card publication | `@Bean AgentCard` + `spring-ai-a2a-server-autoconfigure` |
| Agent card discovery | `A2A.getAgentCard(url, path)` at orchestrator startup |
| Task execution | `DefaultAgentExecutor` wrapping a `ChatClient` |
| Message transport | `JSONRPCTransport` + `JSONRPCTransportConfig` |
| A2A client | `Client.builder(agentCard).withTransport(...)` |
| Delegation tool | `@Tool sendMessage(agentName, task)` on `GameMasterService` |

### Structured Response Format (Chapter 5)

The orchestrator returns structured JSON responses using Java records:

```java
record InquireRequest(String question) {}
record DiceOutput(String diceType, String result, String reason) {}
record StoryOutput(
    String response,
    List<String> actionsSuggestions,
    String details,
    List<DiceOutput> diceRolls
) {}
```

The system prompt instructs the AI to format responses as `StoryOutput` JSON. The controller parses with Jackson and falls back to plain text wrapping if JSON parsing fails.

## Quick Start Checklist

- [ ] Java 25 installed and configured
- [ ] JBang installed (`jbang --version`)
- [ ] AWS credentials configured (`aws configure list`)
- [ ] Bedrock model access enabled in `eu-central-1` (Claude Haiku 4.5 + Titan Embed V2)
- [ ] Chapter 1-3: Run directly with `jbang <FileName>.java`
- [ ] Chapter 4: Start MCP server first, then client in separate terminal
- [ ] Chapter 5: Build knowledge base, then start 4 services in order (MCP → Rules → Character → Orchestrator)
- [ ] Follow immutability practices (records, final parameters, final variables)
- [ ] Use early returns and avoid unnecessary else statements
