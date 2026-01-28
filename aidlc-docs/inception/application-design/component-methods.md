# Component Methods

## Chapter 0: Prerequisites and Setup

### Chapter0Controller
```java
@RestController
@RequestMapping("/api")
class Chapter0Controller {
    
    // Health check endpoint
    ResponseEntity<HealthResponse> checkHealth()
    
    // Bedrock connectivity test
    ResponseEntity<BedrockHealthResponse> testBedrock()
}
```

### BedrockHealthService
```java
@Service
class BedrockHealthService {
    
    // Verify Bedrock API connectivity
    BedrockHealthStatus verifyConnection()
    
    // Test simple Bedrock invocation
    String testInvoke(String prompt)
}
```

---

## Chapter 1: Agent Basics

### Chapter1Controller
```java
@RestController
@RequestMapping("/api/chat")
class Chapter1Controller {
    
    // Send message to agent
    ResponseEntity<ChatResponse> chat(ChatRequest request)
    
    // Reset conversation history
    ResponseEntity<Void> resetConversation()
}
```

### AgentService
```java
@Service
class AgentService {
    
    // Send prompt to agent and get response
    ChatResponse sendMessage(String message)
    
    // Get conversation history
    List<Message> getHistory()
    
    // Clear conversation history
    void clearHistory()
}
```

### ConversationMemory
```java
@Component
class ConversationMemory {
    
    // Add message to history
    void addMessage(Message message)
    
    // Get all messages
    List<Message> getMessages()
    
    // Clear all messages
    void clear()
}
```

---

## Chapter 2: Built-in Tools

### Chapter2Controller
```java
@RestController
@RequestMapping("/api/chat")
class Chapter2Controller {
    
    // Send message to agent with tool access
    ResponseEntity<ChatResponse> chat(ChatRequest request)
    
    // Reset conversation
    ResponseEntity<Void> resetConversation()
}
```

### AgentService
```java
@Service
class AgentService {
    
    // Send message to agent with function calling
    ChatResponse sendMessage(String message)
    
    // Get conversation history including tool calls
    List<Message> getHistory()
    
    // Clear history
    void clearHistory()
}
```

### CharacterStatsFunction
```java
@Component
class CharacterStatsFunction {
    
    // Get character stats by name
    @Tool(description = "Get D&D character statistics")
    CharacterStats getCharacterStats(String characterName)
}
```

---

## Chapter 3: Custom Tools

### Chapter3Controller
```java
@RestController
@RequestMapping("/api/chat")
class Chapter3Controller {
    
    // Send message to dice rolling agent
    ResponseEntity<ChatResponse> chat(ChatRequest request)
    
    // Reset conversation
    ResponseEntity<Void> resetConversation()
}
```

### AgentService
```java
@Service
class AgentService {
    
    // Send message to agent with dice rolling tool
    ChatResponse sendMessage(String message)
    
    // Get conversation history
    List<Message> getHistory()
    
    // Clear history
    void clearHistory()
}
```

### DiceRollingTool
```java
@Component
class DiceRollingTool {
    
    // Roll dice based on notation
    @Tool(description = "Roll D&D dice (e.g., 2d6+3)")
    DiceRollResult rollDice(String diceNotation)
}
```

### DiceNotationParser
```java
@Component
class DiceNotationParser {
    
    // Parse dice notation string
    DiceRoll parse(String notation)
    
    // Validate notation format
    boolean isValid(String notation)
}
```

---

## Chapter 4: MCP Integration

### Chapter4Controller
```java
@RestController
@RequestMapping("/api/chat")
class Chapter4Controller {
    
    // Send message to MCP-enabled agent
    ResponseEntity<ChatResponse> chat(ChatRequest request)
    
    // Reset conversation
    ResponseEntity<Void> resetConversation()
    
    // Get available MCP tools
    ResponseEntity<List<ToolInfo>> getAvailableTools()
}
```

### AgentService
```java
@Service
class AgentService {
    
    // Send message to agent with MCP tools
    ChatResponse sendMessage(String message)
    
    // Get conversation history
    List<Message> getHistory()
    
    // Clear history
    void clearHistory()
}
```

### McpService
```java
@Service
class McpService {
    
    // Connect to MCP server
    void connect(String serverUrl)
    
    // Discover available tools
    List<ToolInfo> discoverTools()
    
    // Invoke MCP tool
    ToolResult invokeTool(String toolName, Map<String, Object> parameters)
    
    // Disconnect from server
    void disconnect()
}
```

### SpellDatabaseTool
```java
@Component
class SpellDatabaseTool {
    
    // Look up spell information via MCP
    @Tool(description = "Look up D&D spell information")
    SpellInfo lookupSpell(String spellName)
}
```

---

## Chapter 5: Agent-to-Agent Communication

### Chapter5Controller
```java
@RestController
@RequestMapping("/api/game")
class Chapter5Controller {
    
    // Start multi-agent game scenario with A2A protocol
    ResponseEntity<GameScenarioResponse> startScenario(GameScenarioRequest request)
    
    // Get game state
    ResponseEntity<GameState> getGameState()
    
    // Get all agent cards (A2A discovery)
    ResponseEntity<List<AgentCard>> getAgentCards()
    
    // Reset game
    ResponseEntity<Void> resetGame()
}
```

### A2AServer
```java
@Service
class A2AServer {
    
    // Handle incoming A2A JSON-RPC 2.0 requests
    JsonRpcResponse handleRequest(JsonRpcRequest request)
    
    // Register agent with A2A server
    void registerAgent(String agentId, AgentCard agentCard)
    
    // Unregister agent
    void unregisterAgent(String agentId)
    
    // Get all registered agent cards
    List<AgentCard> getAgentCards()
}
```

### A2AClient
```java
@Service
class A2AClient {
    
    // Send A2A task request to remote agent
    A2ATaskResponse sendTaskRequest(String targetAgentId, A2ATaskRequest task)
    
    // Discover agents by capability
    List<AgentCard> discoverAgents(String capability)
    
    // Get agent card by ID
    AgentCard getAgentCard(String agentId)
}
```

### AgentCard
```java
@Component
class AgentCard {
    
    // Create agent card with capabilities (A2A standard)
    static AgentCard create(String agentId, String name, List<String> capabilities, String endpoint)
    
    // Get agent capabilities
    List<String> getCapabilities()
    
    // Get agent endpoint
    String getEndpoint()
    
    // Convert to JSON format
    String toJson()
}
```

### A2AMessageHandler
```java
@Component
class A2AMessageHandler {
    
    // Handle A2A JSON-RPC method calls
    Object handleMethod(String method, Map<String, Object> params)
    
    // Validate A2A message format
    boolean validateMessage(JsonRpcRequest request)
    
    // Create A2A response
    JsonRpcResponse createResponse(Object result, String id)
    
    // Create A2A error response
    JsonRpcResponse createError(int code, String message, String id)
}
```

### A2ATaskExecutor
```java
@Service
class A2ATaskExecutor {
    
    // Execute A2A task request
    A2ATaskResult executeTask(A2ATaskRequest task)
    
    // Get task status
    A2ATaskStatus getTaskStatus(String taskId)
    
    // Cancel task
    void cancelTask(String taskId)
}
```

### GameMasterAgent
```java
@Component
class GameMasterAgent {
    
    // Initialize agent with A2A capabilities
    void initialize(A2AServer server, A2AClient client)
    
    // Get agent card
    AgentCard getAgentCard()
    
    // Handle A2A task request
    A2ATaskResult handleTask(A2ATaskRequest task)
    
    // Process player action via A2A
    A2ATaskResult processAction(String action)
    
    // Generate scenario description
    String describeScenario()
}
```

### PlayerAgent
```java
@Component
class PlayerAgent {
    
    // Initialize agent with A2A capabilities
    void initialize(A2AServer server, A2AClient client)
    
    // Get agent card
    AgentCard getAgentCard()
    
    // Handle A2A task request
    A2ATaskResult handleTask(A2ATaskRequest task)
    
    // Decide player action via A2A
    A2ATaskResult decideAction(String situation)
}
```

### RulesExpertAgent
```java
@Component
class RulesExpertAgent {
    
    // Initialize agent with A2A capabilities
    void initialize(A2AServer server, A2AClient client)
    
    // Get agent card
    AgentCard getAgentCard()
    
    // Handle A2A task request
    A2ATaskResult handleTask(A2ATaskRequest task)
    
    // Provide rules clarification via A2A
    A2ATaskResult clarifyRule(String ruleQuery)
}
```

### StorytellerAgent
```java
@Component
class StorytellerAgent {
    
    // Initialize agent with A2A capabilities
    void initialize(A2AServer server, A2AClient client)
    
    // Get agent card
    AgentCard getAgentCard()
    
    // Handle A2A task request
    A2ATaskResult handleTask(A2ATaskRequest task)
    
    // Generate narrative via A2A
    A2ATaskResult generateNarrative(String event)
}
```

### AgentRegistry
```java
@Service
class AgentRegistry {
    
    // Register agent card
    void registerAgentCard(AgentCard card)
    
    // Get all agent cards
    List<AgentCard> getAllAgentCards()
    
    // Find agents by capability
    List<AgentCard> findByCapability(String capability)
    
    // Get agent card by ID
    AgentCard getAgentCard(String agentId)
}
```

### AgentContext
```java
@Component
class AgentContext {
    
    // Store agent-specific context
    void storeContext(String agentId, Map<String, Object> context)
    
    // Retrieve agent context
    Map<String, Object> getContext(String agentId)
    
    // Clear agent context
    void clearContext(String agentId)
}
```

---

## Common Method Patterns

### Configuration Classes
```java
@Configuration
class BedrockChatConfig {
    
    // Create ChatClient bean
    @Bean
    ChatClient chatClient(BedrockConfigProperties properties)
}
```

### Configuration Properties
```java
@ConfigurationProperties(prefix = "bedrock")
class BedrockConfigProperties {
    
    // Getters and setters for:
    // - region
    // - modelId
    // - temperature
    // - maxTokens
}
```

---

## Notes

- All method signatures are high-level interfaces
- Detailed business logic will be defined in Functional Design (per-unit, CONSTRUCTION phase)
- Input/output types use standard Java types and Spring framework classes
- Error handling and validation details deferred to implementation phase
