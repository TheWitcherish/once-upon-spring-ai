# Chapter 3: The Adventurer's Arsenal (JBang Edition)

## Equipping Your AI with Custom Tools

Welcome back, brave adventurer! In this chapter, you will learn to **equip your AI agent with custom tools** - giving it the ability to perform real actions in the world. Your Dungeon Master will now be able to roll actual dice!

## 🚀 Why JBang + Java 25?

This chapter uses **JBang** to make Java development feel like scripting:

- ✅ **No Maven/Gradle setup** - Just run the `.java` file directly
- ✅ **Dependencies in comments** - Declare deps at the top of your file
- ✅ **Instant execution** - `jbang DungeonMasterWithCustomTools.java`
- ✅ **Java 25 features** - Records, var, text blocks, unnamed classes, Markdown docs (`///`)

Perfect for Computer Science students who want to **focus on AI concepts**, not build configuration!

## Learning Objectives

By the end of this chapter, you will be able to:

1. **Create custom tools** for your AI agent using Spring AI's `@Tool` annotation
2. **Define tool inputs and outputs** using Java 25 records
3. **Register tools with ChatClient** for automatic invocation
4. **Understand function calling** - how AI decides when to use tools
5. **See the agentic loop** - AI reasoning, tool use, and response generation

## The Quest Ahead

### The Story

Your Dungeon Master has grown more powerful! No longer limited to mere storytelling, they can now interact with the physical realm - rolling dice to determine the fate of your adventures. When you swing your sword at a goblin, the DM will actually roll the dice to see if you hit!

### What You'll Build

An AI agent that:
- Has access to a **dice rolling tool**
- **Automatically decides** when to use the tool
- Incorporates **real dice results** into the narrative
- Demonstrates the **agentic AI pattern**: Reason → Act → Observe → Respond

## Project Structure

```
chapter3-jbang/
├── DungeonMasterWithCustomTools.java  # Main AI agent program
├── DiceTools.java                     # Reusable dice rolling tool
└── README.md                          # This file
```

This demonstrates **modular code organization** - tools can be reused across multiple AI agents!

This chapter uses:
- **Java 25** - Modern Java with records, var, unnamed classes, Markdown docs
- **JBang** - Run Java like a script
- **Spring AI 2.0.0-M2** - AI integration with function calling
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

## How to Run

### Option 1: Direct Execution (Recommended)

```bash
jbang DungeonMasterWithCustomTools.java
```

That's it! JBang will:
1. Download dependencies (first run only)
2. Compile the code
3. Execute your AI agent

### Option 2: Make it Executable

```bash
chmod +x DungeonMasterWithCustomTools.java
./DungeonMasterWithCustomTools.java
```

### Step 3: Watch the Magic Happen!

Expected output:
```
=== Starting Dungeon Master AI Agent with Custom Tools ===

Player: Help me create a new D&D character! Roll the strength, wisdom, charisma and intelligence abilities scores using 4d6 drop lowest method.

TOOL CALLED: Rolled 4d6: [5, 3, 6, 2] = 16
TOOL CALLED: Rolled 4d6: [4, 4, 1, 5] = 14
TOOL CALLED: Rolled 4d6: [6, 3, 2, 4] = 15
TOOL CALLED: Rolled 4d6: [5, 5, 3, 1] = 14

Dungeon Master says:
*Lady Luck waves her mystical hands over the dice*

Behold, brave adventurer! The fates have spoken for your character:

🎲 **Strength**: Rolled [5, 3, 6, 2] - dropping the 2 = **14** (A solid warrior!)
🎲 **Wisdom**: Rolled [4, 4, 1, 5] - dropping the 1 = **13** (Perceptive and aware!)
🎲 **Charisma**: Rolled [6, 3, 2, 4] - dropping the 2 = **13** (Charming presence!)
🎲 **Intelligence**: Rolled [5, 5, 3, 1] - dropping the 1 = **13** (Sharp mind!)

Your character has balanced abilities - a versatile hero ready for any adventure!
```

Notice the `TOOL CALLED` lines - that's your custom tool being invoked by the AI!

## Understanding the Code

Let's break down the magical elements in `DungeonMasterWithCustomTools.java`.

### 1. JBang Shebang and Dependencies

```java
///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 25+
//SOURCES DiceTools.java
//REPOS mavencentral,spring-milestones=https://repo.spring.io/milestone
//DEPS org.springframework.ai:spring-ai-bedrock-converse:2.0.0-M2
//DEPS org.springframework.ai:spring-ai-client-chat:2.0.0-M2
//DEPS software.amazon.awssdk:bedrockruntime:2.41.24
//DEPS software.amazon.awssdk:auth:2.41.24
//DEPS org.slf4j:slf4j-api:2.0.17
//DEPS org.slf4j:slf4j-simple:2.0.17
```

**What's happening?**
- First line makes the file executable on Unix systems
- `//JAVA 25+` requires Java 25 or higher
- `//SOURCES DiceTools.java` imports the separate tool file - **modular code organization!**
- `//REPOS` adds Spring milestone repository
- `//DEPS` declares Maven dependencies - no `pom.xml` needed!

### 2. Java 25 Records for Tool Data (in DiceTools.java)

```java
/// Record for dice roll output - Java 25 immutable data carrier
public record DiceRollResponse(int[] rolls, int total, String description) {}
```

**What's happening?**
- **Records** (Java 16+, enhanced in 25) are concise immutable data classes
- Automatically generates: constructor, getters, `equals()`, `hashCode()`, `toString()`
- `///` is Java 25's **Markdown documentation comment** syntax
- `public` modifier allows access from the main program
- Perfect for tool inputs/outputs - Spring AI converts to/from JSON automatically

### 3. Modular Tool Organization

**DiceTools.java** is a separate, reusable component:
```java
public class DiceTools {
    // Tool methods here
}
```

**DungeonMasterWithCustomTools.java** imports it:
```java
//SOURCES DiceTools.java

// Later in code:
.tools(new DiceTools())
```

**Benefits:**
- ✅ **Reusability** - Use DiceTools in multiple AI agents
- ✅ **Separation of concerns** - Tools separate from agent logic
- ✅ **Easier testing** - Test tools independently
- ✅ **Better organization** - Students learn modular design

### 3. Creating a Custom Tool with @Tool (in DiceTools.java)

```java
public class DiceTools {

    @Tool(description = "Roll dice for D&D game mechanics. Use this for attack rolls, damage, ability checks, or saving throws.")
    public DiceRollResponse rollDice(
        @ToolParam(description = "Number of faces on the dice (e.g. 6, 20)", required = true) int faces,
        @ToolParam(description = "Number of dice to roll (e.g. 1, 3)", required = true) int count) {
        
        var rolls = new int[count];
        var total = 0;

        for (int i = 0; i < count; i++) {
            rolls[i] = random.nextInt(faces) + 1;
            total += rolls[i];
        }

        var description = "Rolled %dd%d: %s = %d".formatted(count, faces, Arrays.toString(rolls), total);

        log.info("TOOL CALLED: {}", description);

        return new DiceRollResponse(rolls, total, description);
    }
}
```

**What's happening?**
- `@Tool` marks this method as callable by the AI
- `description` tells the AI **when** to use this tool (critical!)
- `@ToolParam` describes each parameter for the AI
- `var` keyword (Java 10+) for local type inference
- `.formatted()` (Java 15+) for string formatting
- Spring AI handles JSON serialization automatically

### 4. Registering the Tool with ChatClient

```java
var agent = ChatClient.builder(chatModel)
    .defaultSystem("""
        You are Lady Luck, the mystical keeper of dice and fortune in D&D adventures.
        You speak with theatrical flair and always announce dice rolls with appropriate drama.
        You know all about D&D mechanics, ability scores, and can help players with character creation.
        When rolling ability scores, remember the traditional method: roll 4d6, drop the lowest die.
        """)
    .build();

var response = agent.prompt()
    .user(playerMessage)
    .tools(new DiceTools())  // <-- Import and instantiate the tool class!
    .call()
    .content();
```

**What's happening?**
- Text blocks (`"""..."""`) for multi-line strings (Java 15+)
- `.tools(new DiceTools())` registers all `@Tool` methods from the class
- The system prompt instructs the AI when to use tools
- The AI autonomously decides to call the tool based on context

### 5. The Agentic Loop (What Happens Behind the Scenes)

When you send "Help me create a new D&D character! Roll ability scores":

```
1. USER MESSAGE    → "Help me create a new D&D character! Roll strength, wisdom..."
                      ↓
2. AI REASONING    → "Need to roll 4d6 four times. I should use rollDice tool."
                      ↓
3. TOOL CALL #1    → rollDice(faces=6, count=4)
                      ↓
4. YOUR CODE RUNS  → Returns: {rolls: [5,3,6,2], total: 16, description: "..."}
                      ↓
5. AI OBSERVES     → "First roll is [5,3,6,2]. Drop lowest (2) = 14 for Strength."
                      ↓
6. TOOL CALL #2-4  → rollDice(faces=6, count=4) [3 more times]
                      ↓
7. AI RESPONDS     → "Your Strength is 14, Wisdom is 13, Charisma is 13..."
```

This is the **agentic pattern**: The AI reasons, acts (calls tools), observes results, and responds.

## Java 25 Features Showcase

This chapter demonstrates modern Java with **modular organization**:

| Feature | Java Version | Example in Code |
|---------|--------------|-----------------|
| **Records** | 16+ | `public record DiceRollResponse(int[] rolls, int total, String description)` |
| **var keyword** | 10+ | `var rolls = new int[count];` |
| **Text Blocks** | 15+ | `"""Multi-line string"""` |
| **Unnamed Classes** | 21+ | Top-level `void main()` without class wrapper |
| **Markdown Docs** | 25 | `/// This is a Markdown comment` |
| **String.formatted()** | 15+ | `"Rolled %dd%d".formatted(count, faces)` |
| **Modular Sources** | JBang | `//SOURCES DiceTools.java` for code organization |

## Key Concepts Summary

### Function Calling / Tool Use

AI models like Claude can be given "tools" - functions they can call:

```
┌─────────────────────────────────────────────────────────┐
│                    YOUR APPLICATION                      │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │
│  │   rollDice  │    │  getWeather │    │  searchDB   │ │
│  │    Tool     │    │    Tool     │    │    Tool     │ │
│  └─────────────┘    └─────────────┘    └─────────────┘ │
│         ▲                  ▲                  ▲        │
│         └──────────────────┼──────────────────┘        │
│                            │                           │
│                    ┌───────┴───────┐                   │
│                    │   AI Model    │                   │
│                    │   (Claude)    │                   │
│                    └───────────────┘                   │
└─────────────────────────────────────────────────────────┘
```

The AI **decides which tools to call** based on:
1. The tool's **name** and **description**
2. The **user's request**
3. The **system prompt** instructions

### Tool Components in Spring AI

| Component | Purpose |
|-----------|---------|
| Record | Define immutable data for tool input/output |
| Tool Class | A class containing `@Tool` annotated methods |
| `@Tool` annotation | Marks a method as callable by the AI |
| `@ToolParam` annotation | Describes individual parameters |
| `.tools()` | Register tool instance with ChatClient |

## Exercises for Adventurers

### Exercise 1: Add a Coin Flip Tool (Easy)

**Quest:** Add a new tool for simple yes/no decisions!

**Tasks:**
1. Create a new file `CoinFlipTools.java` with a `flipCoin` method
2. Add `//SOURCES CoinFlipTools.java` to the main program
3. Register it: `.tools(new DiceTools(), new CoinFlipTools())`
4. Test with: "Should I go left or right? Flip a coin!"

**Starter code for CoinFlipTools.java:**
```java
///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 25+

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import java.util.Random;

public class CoinFlipTools {
    private static final Random random = new Random();
    
    public record CoinFlipResponse(String result, String answer) {}

    @Tool(description = "Flip a coin to make a random yes/no decision")
    public CoinFlipResponse flipCoin(@ToolParam(description = "The question to decide") String question) {
        var result = random.nextBoolean() ? "HEADS" : "TAILS";
        return new CoinFlipResponse(result, "The coin shows " + result);
    }
}
```

### Exercise 2: Add Damage Dice (Medium)

**Quest:** Make the DM roll damage after a successful attack!

**Tasks:**
1. Modify the player message to ask about combat
2. Watch the AI call rollDice multiple times (attack + damage)

**Example message:**
```java
var playerMessage = "I attack the goblin with my longsword. Roll 1d20 for attack and 1d8 for damage!";
```

### Exercise 3: Create an Inventory Tool (Hard)

**Quest:** Let the AI check the player's inventory!

**Tasks:**
1. Create `InventoryTools.java` with a static inventory map
2. Create `checkInventory` tool that returns item counts
3. Add to main program and test with: "Do I have any health potions?"

**Starter code for InventoryTools.java:**
```java
///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 25+

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import java.util.Map;

public class InventoryTools {
    private static final Map<String, Integer> inventory = Map.of(
        "Health Potion", 3,
        "Gold", 50,
        "Sword", 1
    );
    
    public record InventoryResponse(String item, int count, boolean hasItem) {}

    @Tool(description = "Check the player's inventory for items")
    public InventoryResponse checkInventory(@ToolParam(description = "Item name to check") String itemName) {
        var count = inventory.getOrDefault(itemName, 0);
        return new InventoryResponse(itemName, count, count > 0);
    }
}
```

### Exercise 4: Multiple Dice Rolls (Hard)

**Quest:** Have a full combat encounter!

**Tasks:**
1. Send a message that triggers multiple tool calls
2. Example: "I attack with my greatsword (1d20 to hit, 2d6 damage), then the goblin counterattacks (1d20 to hit, 1d6 damage)"
3. Watch the AI orchestrate multiple tool calls!

## JBang Pro Tips

### Edit with IDE Support

```bash
# Generate IDE project files
jbang edit --open=idea DungeonMasterWithCustomTools.java  # IntelliJ
jbang edit --open=code DungeonMasterWithCustomTools.java  # VS Code
```

### Cache Dependencies

First run downloads dependencies. Subsequent runs are instant!

```bash
# Clear cache if needed
jbang cache clear
```

### Run with Different Java Versions

```bash
# Use specific Java version
jbang --java 25 DungeonMasterWithCustomTools.java
```

### Debug Mode

```bash
# Enable debug logging
jbang --debug DungeonMasterWithCustomTools.java
```

## Troubleshooting

### "Java 25 not found"

Ensure Java 25 is installed and set as default:
```bash
java -version
export JAVA_HOME=$(/usr/libexec/java_home -v 25)  # macOS
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

- Verify tool description clearly explains when to use it
- Check system prompt instructs the AI to use tools
- Look for "TOOL CALLED" in output to confirm invocation

### "Dependencies not downloading"

```bash
# Clear JBang cache and retry
jbang cache clear
jbang DungeonMasterWithCustomTools.java
```

## Why This Approach Rocks for Students

| Traditional Maven/Gradle | JBang + Java 25 |
|---------------------------|-----------------|
| Create project structure | Single `.java` file |
| Edit `pom.xml` or `build.gradle` | Dependencies in comments |
| `mvn clean install` | `jbang run` |
| Wait for build | Instant execution |
| Complex IDE setup | Works with any editor |
| Focus on build tools | **Focus on AI concepts** |

**Result:** Students spend time learning AI agents, not fighting build systems!

## Official Documentation

- [JBang Documentation](https://www.jbang.dev/documentation/) - JBang guide
- [Spring AI Function Calling](https://docs.spring.io/spring-ai/reference/api/functions.html) - Tool/Function calling
- [AWS Bedrock Tool Use](https://docs.aws.amazon.com/bedrock/latest/userguide/tool-use.html) - Bedrock tools
- [Java 25 Features](https://openjdk.org/projects/jdk/25/) - What's new in Java 25

## Next Steps

Ready for more? Check out:
- **Chapter 4** - Build an MCP Server to expose tools over HTTP
- Experiment with more complex tools (API calls, database queries)
- Combine multiple tools for sophisticated agent behaviors

Your arsenal grows stronger, adventurer! 🎲✨

---

**Questions?** Remember:
1. Tool descriptions guide AI decisions
2. System prompts instruct when to use tools
3. The agentic loop: Reason → Act → Observe → Respond
4. JBang makes Java feel like Python - just run it!
