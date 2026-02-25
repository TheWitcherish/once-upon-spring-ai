# Chapter 4: The Tavern Notice Board (JBang Edition)

## Connecting AI Agents via MCP

Welcome back, brave adventurer! In this chapter, you will learn how to **expose tools as a remote service** using the **Model Context Protocol (MCP)** and connect an AI agent to them over the network. Your dice-rolling tools now live in a tavern notice board that any adventurer can discover and use!

## 🚀 Why JBang + MCP?

This chapter uses **JBang** to simplify running both the MCP Server and Client:

- ✅ **No Maven build** - Just run `.java` files directly
- ✅ **Fast iteration** - Edit code, run immediately
- ✅ **Easy debugging** - Add print statements and see results instantly
- ✅ **Two simple commands** - One for server, one for client
- ✅ **Spring Boot magic** - Full MCP Server in a single file

Perfect for Computer Science students who want to **experiment with distributed AI systems** without complex setup!

## Learning Objectives

By the end of this chapter, you will be able to:

1. **Build an MCP Server** using Spring Boot that exposes tools over Streamable HTTP
2. **Use `@McpTool` annotations** to define MCP tools
3. **Create a standalone MCP Client** that discovers and uses remote tools
4. **Bridge MCP tools into Spring AI** using `SyncMcpToolCallbackProvider`
5. **Understand the MCP protocol flow** - discovery, invocation, and response

## The Quest Ahead

### The Story

The tavern keeper has posted a magical notice board - a service that any adventurer (AI agent) in the realm can consult. Instead of carrying their own dice, adventurers can now call upon the tavern's enchanted dice from anywhere! The notice board advertises what services are available, and adventurers discover them dynamically.

### What You'll Build

Two programs that work together:

1. **MCP Server** (`DiceRollMcpServer.java`) - A Spring Boot application that:
   - Exposes a `rollDice` tool over Streamable HTTP
   - Advertises its capabilities via the MCP protocol
   - Runs on `http://localhost:8080/mcp`

2. **MCP Client** (`DungeonMasterMCPClient.java`) - A standalone Java program that:
   - **Discovers tools** from the remote MCP Server
   - **Bridges them into Spring AI** as tool callbacks
   - Lets an AI agent (Lady Luck) **invoke remote tools** during conversation

## Tech Stack

This chapter uses:
- **Java 25** - Modern Java with unnamed classes, records, var
- **JBang** - Run Java like a script
- **Spring Boot 4.0** - For the MCP Server application
- **Spring AI 2.0.0-M2** - MCP integration and Bedrock chat model
- **MCP Java SDK 0.17.2** - Model Context Protocol client/server
- **Spring AI Community MCP Annotations** - `@McpTool` for declarative tool definitions
- **AWS Bedrock** - Claude Haiku 4.5 model hosting

## Prerequisites

1. **Java 25** installed
   ```bash
   java -version  # Should show: java version "25"
   ```

2. **JBang** installed
   ```bash
   # macOS
   brew install jbangdev/tap/jbang
   
   # Linux/WSL
   curl -Ls https://sh.jbang.dev | bash -s - app setup
   
   # Windows
   choco install jbang
   
   # Verify installation
   jbang version
   ```

3. **AWS Account** with Bedrock access
   - AWS credentials configured via `~/.aws/credentials` or environment variables
   - Bedrock model access enabled for Claude Haiku 4.5 in `us-east-1`

4. **Two terminal windows** - one for the server, one for the client

## How to Run

### Step 1: Start the MCP Server (Terminal 1)

```bash
cd chapter4-jbang
jbang DiceRollMcpServer.java
```

You should see output like:
```
Started DiceRollMcpServer in X seconds (process running on 12345)
```

**Keep this terminal running!** The server must stay alive for the client to connect.

### Step 2: Run the MCP Client (Terminal 2)

Open a **new terminal** in the same `chapter4-jbang` directory and run:

```bash
jbang DungeonMasterMCPClient.java
```

### Step 3: Watch the Magic Happen!

**Terminal 2 (Client) output:**
```
=== Starting Dungeon Master MCP Client ===

Connecting to D&D Dice Roll MCP Server...
Available tools: [rollDice]

Player: I want to attack the goblin with my sword! Roll a d20 for me!

Lady Luck says:
*The air crackles with anticipation...*
You rolled a 15! A solid strike, adventurer!
```

**Terminal 1 (Server) output:**
```
DICE ROLL: Rolled 1d20: [15] = 15
```

The dice roll happened on the **server** but was used by the AI agent on the **client**! 🎲✨

## Project Structure

```
chapter4-jbang/
├── DiceRollMcpServer.java         # MCP Server (Spring Boot)
├── DiceTools.java                 # Dice rolling tool (reusable)
├── DungeonMasterMCPClient.java    # MCP Client (AI agent)
├── application.properties         # Spring Boot configuration
└── README.md                      # This file
```

## Understanding the Code

### The MCP Server: `DiceRollMcpServer.java`

#### 1. JBang + Spring Boot

```java
///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 25+
//SOURCES DiceTools.java
//REPOS mavencentral,spring-milestones=https://repo.spring.io/milestone
//DEPS org.springframework.boot:spring-boot-starter-web:4.0.2
//DEPS org.springframework.ai:spring-ai-starter-mcp-server-webmvc:2.0.0-M2
//DEPS org.springaicommunity:mcp-annotations:0.8.0
//DEPS io.modelcontextprotocol.sdk:mcp:0.17.2

@SpringBootApplication
public class DiceRollMcpServer {
    public static void main(final String[] args) {
        SpringApplication.run(DiceRollMcpServer.class, args);
    }
}
```

**What's happening?**
- `//SOURCES DiceTools.java` - Import the tool class
- `//DEPS` - Spring Boot + MCP Server dependencies
- `@SpringBootApplication` - Full Spring Boot app in one file!
- JBang handles all the complexity - just run it!

#### 2. Configuration: `application.properties`

```properties
spring.ai.mcp.server.name=dnd-dice-mcp-server
spring.ai.mcp.server.protocol=STATELESS
spring.ai.mcp.server.type=SYNC
server.port=8080
```

**What's happening?**
- MCP Server configuration - name, protocol, type
- Server runs on port 8080
- JBang automatically finds this file in the same directory

#### 3. Declaring Tools: `DiceTools.java`

```java
@Component
public class DiceTools {

    @McpTool(name = "rollDice", description = "Roll dice for D&D game mechanics.")
    public DiceRollResponse rollDice(
            @McpToolParam(description = "Number of faces on the dice") final int faces,
            @McpToolParam(description = "Number of dice to roll") final int count) {
        // ... roll logic ...
        return new DiceRollResponse(rolls, total, description);
    }
}
```

**What's different from Chapter 3?**
- `@McpTool` instead of `@Tool` - exposes the method via MCP protocol
- `@McpToolParam` describes each parameter for remote clients
- `@Component` - Spring auto-discovers this tool
- Tool runs on the **server**, not in the client process

### The MCP Client: `DungeonMasterMCPClient.java`

#### 1. JBang Dependencies

```java
//JAVA 25+
//REPOS mavencentral,spring-milestones=https://repo.spring.io/milestone
//DEPS org.springframework.ai:spring-ai-bedrock-converse:2.0.0-M2
//DEPS org.springframework.ai:spring-ai-client-chat:2.0.0-M2
//DEPS org.springframework.ai:spring-ai-mcp:2.0.0-M2
//DEPS io.modelcontextprotocol.sdk:mcp:0.17.2
//DEPS software.amazon.awssdk:bedrockruntime:2.41.24
```

**What's happening?**
- Spring AI for chat client and MCP integration
- MCP Java SDK for protocol communication
- AWS SDK for Bedrock access
- No Spring Boot here - just a standalone script!

#### 2. Connecting to the MCP Server

```java
var transport = HttpClientStreamableHttpTransport.builder("http://localhost:8080")
        .endpoint("/mcp")
        .build();

var mcpClient = McpClient.sync(transport)
        .clientInfo(new McpSchema.Implementation("dnd-dice-mcp-client", "1.0.0"))
        .build();

mcpClient.initialize();
```

**What's happening?**
- `HttpClientStreamableHttpTransport` - Modern MCP transport over HTTP
- `McpClient.sync()` - Synchronous MCP client
- `initialize()` - Performs MCP handshake with server

#### 3. Discovering Remote Tools

```java
var toolsResult = mcpClient.listTools();
var toolNames = toolsResult.tools().stream().map(McpSchema.Tool::name).toList();
log.info("Available tools: {}", toolNames);
// Output: Available tools: [rollDice]
```

**What's happening?**
- Client **asks the server** what tools are available
- No hardcoding - tools discovered at runtime!
- Add more tools to the server, client automatically sees them

#### 4. Bridging MCP Tools into Spring AI

```java
var mcpToolProvider = SyncMcpToolCallbackProvider.builder()
        .mcpClients(mcpClient)
        .build();
var mcpTools = mcpToolProvider.getToolCallbacks();
```

**This is the key bridge!**
- `SyncMcpToolCallbackProvider` converts MCP tools → Spring AI `ToolCallback`
- AI agent doesn't know (or care) that tools are remote
- Transparent network communication

#### 5. Using Remote Tools with the AI Agent

```java
var response = agent.prompt()
        .user(playerMessage)
        .toolCallbacks(mcpTools)  // Remote tools, used like local ones!
        .call()
        .content();
```

**What's happening?**
- `.toolCallbacks(mcpTools)` - Use remote tools like local tools
- AI decides when to call tools based on user message
- MCP layer handles all network communication
- Results flow back to the AI for response generation

### The MCP Flow (What Happens Behind the Scenes)

```
┌─────────────────────┐                    ┌─────────────────────┐
│     MCP CLIENT      │                    │     MCP SERVER      │
│  (AI Agent + LLM)   │                    │  (Spring Boot App)  │
│                     │                    │                     │
│  1. initialize()  ──┼──── Handshake ────►│  Capabilities       │
│                     │◄─── Response ──────┼  negotiated         │
│                     │                    │                     │
│  2. listTools()   ──┼──── Discovery ────►│  Returns:           │
│                     │◄─── Tool List ─────┼  [{rollDice, ...}]  │
│                     │                    │                     │
│  3. AI decides to ──┼──── Tool Call ────►│  DiceTools           │
│     call rollDice   │◄─── Result ────────┼  .rollDice(20, 1)  │
│                     │                    │  → {rolls:[15],...} │
│  4. AI responds     │                    │                     │
│     with result     │                    │                     │
└─────────────────────┘                    └─────────────────────┘
```

## Key Concepts Summary

### MCP (Model Context Protocol)

MCP is an open protocol that standardizes how AI agents discover and use tools:

| Concept | Description |
|---------|-------------|
| **MCP Server** | Exposes tools, resources, and prompts over a standard protocol |
| **MCP Client** | Connects to servers and invokes tools on behalf of AI agents |
| **Transport** | How client and server communicate (Streamable HTTP, stdio, SSE) |
| **Tool Discovery** | Clients dynamically discover available tools at runtime |
| **Handshake** | Client and server negotiate capabilities on connection |

### Local Tools vs MCP Tools

| Aspect | Chapter 3 (Local) | Chapter 4 (MCP) |
|--------|-------------------|------------------|
| Annotation | `@Tool` | `@McpTool` |
| Registration | `.tools(new DiceTools())` | `.toolCallbacks(mcpTools)` |
| Where tools run | Same process as AI agent | Remote server over HTTP |
| Discovery | Compile-time | Runtime via `listTools()` |
| Sharing | Single application | Any MCP client can connect |
| Debugging | Single process | Two processes (server + client) |

### Why MCP Matters

Think of it like a **universal adapter** for AI tools:
- **Without MCP**: Every AI framework has its own way to define and call tools
- **With MCP**: Define tools once, use them from any MCP-compatible AI agent
- **Analogy**: MCP is to AI tools what REST is to web APIs

## JBang Pro Tips

### Run Server in Background

```bash
# Start server in background
jbang DiceRollMcpServer.java &

# Run client
jbang DungeonMasterMCPClient.java

# Stop server
pkill -f DiceRollMcpServer
```

### Debug Mode

```bash
# Server with debug logging
jbang --debug DiceRollMcpServer.java

# Client with verbose output
jbang DungeonMasterMCPClient.java 2>&1 | tee client.log
```

### Edit with IDE Support

```bash
# Generate IDE project files
jbang edit --open=idea DiceRollMcpServer.java  # IntelliJ
jbang edit --open=code DiceRollMcpServer.java  # VS Code
```

### Change Server Port

Edit `application.properties`:
```properties
server.port=3000  # Use port 3000 instead
```

Then update client:
```java
var transport = HttpClientStreamableHttpTransport.builder("http://localhost:3000")
```

## Exercises for Adventurers

### Exercise 1: Add a Second Tool to the Server (Easy)

**Quest:** Add a `flipCoin` tool to the MCP Server!

**Tasks:**
1. Edit `DiceTools.java` and add a new `@McpTool` method
2. Restart the server: `Ctrl+C` then `jbang DiceRollMcpServer.java`
3. Run the client and check that `listTools()` now returns `[rollDice, flipCoin]`
4. Change the player message to: "Should I open the mysterious chest? Flip a coin!"

**Starter code for DiceTools.java:**
```java
public record CoinFlipResponse(String result, String answer) {}

@McpTool(name = "flipCoin", description = "Flip a coin for random yes/no decisions")
public CoinFlipResponse flipCoin() {
    var result = random.nextBoolean() ? "HEADS" : "TAILS";
    log.info("COIN FLIP: {}", result);
    return new CoinFlipResponse(result, "The coin shows " + result);
}
```

### Exercise 2: Multiple Player Actions (Medium)

**Quest:** Modify the client to send multiple player messages in sequence!

**Tasks:**
1. Create a list of player actions in `DungeonMasterMCPClient.java`:
   ```java
   var actions = List.of(
       "I attack the goblin! Roll 1d20.",
       "I cast a fireball! Roll 8d6 for damage.",
       "I pick the lock. Roll 1d20 for dexterity check."
   );
   ```
2. Loop through them, sending each to the AI agent
3. Observe how the AI calls the remote dice tool for each action

### Exercise 3: Create an Inventory Tool (Hard)

**Quest:** Add a new tool that checks player inventory!

**Tasks:**
1. Create `InventoryTools.java` with a static inventory map
2. Add `@McpTool` method `checkInventory`
3. Add `//SOURCES InventoryTools.java` to the server
4. Restart server and test with: "Do I have any health potions?"

**Starter code for InventoryTools.java:**
```java
///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 25+

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import java.util.Map;

@Component
public class InventoryTools {
    private static final Logger log = LoggerFactory.getLogger("InventoryTools");
    private static final Map<String, Integer> inventory = Map.of(
        "Health Potion", 3,
        "Gold", 50,
        "Sword", 1
    );
    
    public record InventoryResponse(String item, int count, boolean hasItem) {}

    @McpTool(name = "checkInventory", description = "Check the player's inventory for items")
    public InventoryResponse checkInventory(
            @McpToolParam(description = "Item name to check") String itemName) {
        var count = inventory.getOrDefault(itemName, 0);
        log.info("INVENTORY CHECK: {} -> {}", itemName, count);
        return new InventoryResponse(itemName, count, count > 0);
    }
}
```

Then update `DiceRollMcpServer.java`:
```java
//SOURCES DiceTools.java
//SOURCES InventoryTools.java  // Add this line
```

### Exercise 4: Error Handling (Medium)

**Quest:** What happens when the MCP Server is down?

**Tasks:**
1. Stop the MCP Server (`Ctrl+C` in Terminal 1)
2. Run the MCP Client and observe the error
3. Add a try-catch around `mcpClient.initialize()` in the client
4. Print a friendly error message: "Server not available. Please start DiceRollMcpServer.java first."

**Starter code:**
```java
try {
    mcpClient.initialize();
    log.info("Connected to MCP Server!");
} catch (Exception e) {
    log.error("❌ Cannot connect to MCP Server at http://localhost:8080/mcp");
    log.error("💡 Please start the server first: jbang DiceRollMcpServer.java");
    System.exit(1);
}
```

## Troubleshooting

### "Port 8080 already in use"

Another process is using port 8080:
```bash
# Find and kill the process
lsof -ti:8080 | xargs kill -9

# Or change the port in application.properties
server.port=3000
```

### "Cannot connect to MCP Server"

Make sure the server is running:
```bash
# Terminal 1
jbang DiceRollMcpServer.java

# Wait for "Started DiceRollMcpServer" message

# Terminal 2
jbang DungeonMasterMCPClient.java
```

### "AWS credentials not found"

Configure AWS credentials:
```bash
aws configure
# Or set environment variables
export AWS_ACCESS_KEY_ID=your-key
export AWS_SECRET_ACCESS_KEY=your-secret
export AWS_REGION=us-east-1
```

### "Tool not being called"

- Check server logs for "DICE ROLL:" messages
- Verify tool description is clear
- Ensure client successfully lists tools: `Available tools: [rollDice]`
- Try a more explicit user message: "Roll 1d20 for attack"

### "JBang dependencies not downloading"

```bash
# Clear JBang cache
jbang cache clear

# Re-run with verbose output
jbang --verbose DiceRollMcpServer.java
```

## Why This Approach Rocks for Students

| Traditional Maven/Gradle | JBang + MCP |
|---------------------------|-------------|
| Complex multi-module setup | Two simple `.java` files |
| `mvn clean install` | `jbang run` |
| Edit → Build → Run cycle | Edit → Run instantly |
| Hard to debug distributed systems | Add `log.info()`, see results immediately |
| Focus on build configuration | **Focus on MCP concepts** |

**Result:** Students experiment with distributed AI systems without fighting build tools!

## Official Documentation

- [JBang Documentation](https://www.jbang.dev/documentation/) - JBang guide
- [MCP Specification](https://modelcontextprotocol.io/) - Model Context Protocol spec
- [MCP Java SDK](https://github.com/modelcontextprotocol/java-sdk) - Java implementation
- [Spring AI MCP Integration](https://docs.spring.io/spring-ai/reference/api/mcp.html) - Spring AI MCP guide
- [Spring AI Community MCP Annotations](https://github.com/spring-ai-community/mcp-annotations) - `@McpTool` library

## Next Steps

Ready for more? Try:
- **Connect multiple MCP servers** to one client
- **Build a web UI** that uses the MCP Server
- **Deploy the MCP Server** to AWS (Lambda, ECS, etc.)
- **Create your own MCP tools** (weather, database, APIs)

Your tavern notice board is now open for business! 🏰✨

---

**Questions?** Remember:
1. Server must run **before** client connects
2. Tools are discovered at **runtime**, not compile-time
3. MCP makes tools **shareable** across any AI agent
4. JBang makes Java feel like **Python** - just run it!
