# Once Upon Spring AI - Development Guide

This guide provides comprehensive information about the "Once Upon Spring AI" workshop project, including project overview, build commands, code style guidelines, and testing instructions.

## Project Overview

**Once Upon Spring AI** is a hands-on workshop series teaching developers how to build AI agents using **Java 25** and **Spring AI 2.0** with **AWS Bedrock**. The workshop uses D&D (Dungeons & Dragons) storytelling as a creative theme to progressively introduce AI agent development concepts.

### Tech Stack

- **Java 25** - Modern Java with records, unnamed classes, var keyword, and text blocks
- **Spring AI 2.0.0-M2** - Spring's AI/ML integration framework
- **Spring Boot 3.4.3** - For Chapter 4 (MCP Server)
- **AWS Bedrock** - Cloud-based AI model hosting
- **Claude Haiku 4.5** (Anthropic) - AI model via Bedrock (`global.anthropic.claude-haiku-4-5-20251001-v1:0`)
- **Maven** - Build tool (included via Maven Wrapper)

### Workshop Structure

The project contains 4 progressive chapters, each building upon the previous:

| Chapter | Title | Description | Key Concepts |
|---------|-------|-------------|--------------|
| **Chapter 1** | The Art of Agent Summoning | Create your first AI agent - a Dungeon Master chatbot | ChatClient, System Prompts, AWS Bedrock Integration |
| **Chapter 2** | AI Agent with Built-in Tools | Equip agent with SmartWebFetchTool for web content fetching | Community Tools, Tool Registration, Autonomous Tool Use |
| **Chapter 3** | The Adventurer's Arsenal | Build custom tools (dice rolling) for AI agents | Custom Tools, @Tool Annotation, Function Calling, Agentic Loop |
| **Chapter 4** | The Tavern Notice Board | Expose tools as MCP Server over Streamable HTTP | MCP Protocol, @McpTool/@McpToolParam, Spring Boot, Network Services |

### Project Structure

```
once-upon-spring-ai/
├── README.md                    # Main project documentation
├── AGENTS.md                    # This file - development guide
├── LICENSE                      # Project license
├── .gitignore                   # Git ignore rules
├── chapter1/                    # Chapter 1: Simple AI Agent
│   ├── README.md                # Chapter-specific instructions
│   ├── pom.xml                  # Maven dependencies (standalone Spring AI)
│   ├── DungeonMasterSimple.java # Main source file (unnamed class)
│   ├── mvnw, mvnw.cmd           # Maven wrapper scripts
│   └── .mvn/                    # Maven wrapper configuration
├── chapter2/                    # Chapter 2: Built-in Tools
│   ├── README.md
│   ├── pom.xml                  # Includes spring-ai-agent-utils
│   ├── DungeonMasterWithBuiltInTools.java
│   ├── mvnw, mvnw.cmd
│   └── .mvn/
├── chapter3/                    # Chapter 3: Custom Tools
│   ├── README.md
│   ├── pom.xml
│   ├── DungeonMasterWithCustomTools.java
│   ├── mvnw, mvnw.cmd
│   └── .mvn/
└── chapter4/                    # Chapter 4: MCP Server
    ├── README.md
    ├── pom.xml                  # Spring Boot with MCP Server starter
    ├── mvnw, mvnw.cmd
    ├── .mvn/
    └── src/
        └── main/
            ├── java/
            │   └── DiceRollMcpServer.java
            └── resources/
                └── application.properties
```

### Prerequisites

1. **Java 25 or higher** installed
   ```bash
   java -version  # Should show: java version "25" or higher
   ```

2. **AWS Account** with Bedrock access
   - AWS credentials configured via `~/.aws/credentials` or environment variables
   - Bedrock model access enabled for Claude Haiku 4.5 in your region

3. **Maven** (included via Maven Wrapper in each chapter)

## Build and Test Commands

### Chapter 1, 2, 3 (Standalone Java Applications)

These chapters use unnamed Java classes that can be run directly without compilation.

#### Download Dependencies

```bash
cd chapter1  # or chapter2, chapter3
./mvnw dependency:copy-dependencies -DoutputDirectory=target/dependency
```

#### Run the Application

```bash
# Run with filtered warnings
java --class-path "target/dependency/*" DungeonMasterSimple.java 2>&1 | grep -v "^WARNING:"

# Or run with all output
java --class-path "target/dependency/*" DungeonMasterSimple.java
```

Replace `DungeonMasterSimple.java` with:
- `DungeonMasterWithBuiltInTools.java` for Chapter 2
- `DungeonMasterWithCustomTools.java` for Chapter 3

#### Clean Build Artifacts

```bash
./mvnw clean
```

### Chapter 4 (Spring Boot MCP Server)

Chapter 4 uses Spring Boot and requires packaging as a JAR.

#### Build the Application

```bash
cd chapter4
./mvnw clean package -DskipTests
```

#### Run the MCP Server

Use Maven Spring Boot plugin
```bash
./mvnw spring-boot:run
```

The server will start on `http://localhost:8080/mcp`

#### Test the MCP Server

Configure an MCP client (e.g., Claude Desktop) in `claude_desktop_config.json`:

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

#### Stop the Server

Press `Ctrl+C` in the terminal

### Common Maven Commands

```bash
# Display project dependencies
./mvnw dependency:tree

# Display effective POM
./mvnw help:effective-pom

# Update Maven wrapper
./mvnw wrapper:wrapper

# Verify project configuration
./mvnw validate
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
- Avoid `var` keyword, prefer explicit types
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

1. **Run the application** following the build commands above
2. **Observe the output** - AI responses should be logged to console
3. **Verify tool calls** - Look for "TOOL CALLED" messages in Chapter 3
4. **Check AWS connectivity** - Ensure Bedrock API calls succeed

Expected behaviors:
- **Chapter 1**: AI responds with D&D narrative
- **Chapter 2**: AI fetches Wikipedia content and extracts information
- **Chapter 3**: AI calls dice rolling tool and incorporates results into narrative

#### Chapter 4: MCP Server Testing

Test the MCP Server using an MCP client:

1. **Start the server**:
   ```bash
   cd chapter4
   ./mvnw spring-boot:run
   ```

2. **Verify server is running**:
   - Check console output for "D&D Dice Roll MCP Server running at http://localhost:8080/mcp"
   - Server should be listening on port 8080

3. **Test with MCP client**:
   - Configure Claude Desktop or another MCP client
   - Connect to `http://localhost:8080/mcp`
   - Verify `roll_dice` tool is discovered
   - Invoke the tool and check server logs for "DICE ROLL" messages

4. **Manual API testing** (optional):
   ```bash
   # Test server health
   curl http://localhost:8080/actuator/health
   ```

### Troubleshooting Tests

#### AWS Credentials Issues
```bash
# Verify credentials are configured
aws configure list

# Test Bedrock access
aws bedrock list-foundation-models --region eu-central-1
```

#### Port Conflicts (Chapter 4)
If port 8080 is in use:
1. Edit `chapter4/src/main/resources/application.properties`
2. Change `server.port=8080` to another port (e.g., `server.port=3000`)
3. Rebuild and restart

#### Tool Not Being Called (Chapter 3)
- Verify system prompt instructs AI to use tools
- Check tool description is clear and relevant
- Ensure user message requires tool usage (e.g., "attack the goblin" should trigger dice roll)

## Logging

- Use `@Slf4j` annotation from Lombok for logging to avoid boilerplate code with Logger instances
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

Example Custom Tool (Chapter 3):

```java
class DiceTools {
    record DiceRollRequest(int numberOfDice, int sides) {}
    record DiceRollResponse(int[] rolls, int total, String description) {}

    @Tool(description = "Roll dice for D&D game mechanics. Use this for attack rolls, damage, ability checks, or saving throws.")
    DiceRollResponse rollDice(final DiceRollRequest request) {
        // Implementation
    }
}

// Register with ChatClient
var agent = ChatClient.builder(chatModel)
    .defaultTools(new DiceTools())
    .build();
```

### For MCP Servers (Chapter 4)

- Use `@McpTool` to expose methods as MCP (Model Context Protocol) tools
- Use `@McpToolParam` to describe individual parameters with clear descriptions
- Annotate tool classes with `@Component` for auto-discovery by Spring Boot
- Configure MCP server properties in `application.properties`:
  - `spring.ai.mcp.server.name` - Server name
  - `spring.ai.mcp.server.version` - Server version
  - `spring.ai.mcp.server.protocol` - Transport protocol (STREAMABLE recommended)
  - `spring.ai.mcp.server.type` - Server type (SYNC or ASYNC)

Example MCP Tool (Chapter 4):

```java
@Component
public class DiceTools {
    @McpTool(name = "roll_dice", description = "Roll multiple dice with a specified number of faces")
    public Object rollDice(
            @McpToolParam(description = "Number of faces on the dice") final int faces,
            @McpToolParam(description = "Number of dice to roll") final int count) {
        // Implementation
    }
}
```

### Key Differences: @Tool vs @McpTool

| Aspect | @Tool (Chapters 1-3) | @McpTool (Chapter 4) |
|--------|---------------------|----------------------|
| Scope | Embedded in application | Exposed as network service |
| Parameters | Single request record | Individual `@McpToolParam` per parameter |
| Discovery | Manual registration | Auto-discovered by Spring Boot |
| Protocol | Internal Spring AI | Standard MCP protocol |
| Clients | Only your ChatClient | Any MCP-compatible client |
| Use Case | Single application | Shared tools across multiple clients |

### AWS Bedrock Configuration

All chapters use AWS Bedrock with Claude Haiku 4.5:

```java
// Create Bedrock client
var bedrockClient = BedrockRuntimeClient.builder()
    .region(Region.EU_CENTRAL_1)
    .credentialsProvider(DefaultCredentialsProvider.builder().build())
    .build();

// Configure model
var modelId = "global.anthropic.claude-haiku-4-5-20251001-v1:0";
var options = BedrockChatOptions.builder()
    .model(modelId)
    .build();

// Create ChatModel
var chatModel = BedrockProxyChatModel.builder()
    .bedrockRuntimeClient(bedrockClient)
    .defaultOptions(options)
    .build();
```

## Quick Start Checklist

- [ ] Java 25 installed and configured
- [ ] Spring Boot 3.4+ project created
- [ ] Lombok dependency added
- [ ] Choose mapper strategy (MapStruct or static)
- [ ] Configure logging with SLF4J
- [ ] Set up exception handling with `@ControllerAdvice`
- [ ] Write tests with JUnit 5 and Mockito
- [ ] Follow immutability and final variable practices
- [ ] Use early returns and avoid unnecessary else statements
