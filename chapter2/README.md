# Chapter 2: The Adventurer's Arsenal

## Equipping Your AI with Tools

Welcome back, brave adventurer! In this chapter, you will learn to **equip your AI agent with tools** - giving it the ability to perform real actions in the world. Your Dungeon Master will now be able to roll actual dice!

## Learning Objectives

By the end of this chapter, you will be able to:

1. **Create built-in tools** for your AI agent using Spring AI
2. **Define tool inputs and outputs** using Java records
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

## Tech Stack

This chapter uses:
- **Java 25** - Modern Java with unnamed classes
- **Spring AI 2.0.0-M1** - AI integration with function calling
- **AWS Bedrock** - Claude model hosting with tool use support

## Prerequisites

Same as Chapter 1:
1. **Java 25** installed
2. **Maven** installed
3. **AWS Account** with Bedrock access and credentials configured

## How to Run

### Step 1: Download Dependencies

```bash
./mvnw dependency:copy-dependencies -DoutputDirectory=target/dependency
```

### Step 2: Run Your Program

```bash
java --class-path "target/dependency/*" DungeonMasterWithTools.java 2>&1 | grep -v "^WARNING:"
```

### Step 3: Watch the Magic Happen!

Expected output:
```
=== Starting Dungeon Master AI Agent with Tools ===

========================================
   Welcome to the D&D AI Adventure!
        (Tools Edition)
========================================

Player: I want to attack the goblin with my sword! What happens?

TOOL CALLED: Rolled 1d20: [15] = 15

Dungeon Master says:
You grip your sword tightly and lunge at the goblin!
*rolls dice* You rolled a 15 on your attack roll - a solid hit!
The goblin shrieks as your blade finds its mark...
[AI continues narrating based on the actual dice result]
```

Notice the `TOOL CALLED` line - that's your tool being invoked by the AI!

## Understanding the Code

Let's break down the new magical elements in `DungeonMasterWithTools.java`.

### 1. Defining Tool Input/Output with Records

```java
// What the AI sends to our tool
record DiceRollRequest(int numberOfDice, int sides) {}

// What we return to the AI
record DiceRollResponse(int[] rolls, int total, String description) {}
```

**What's happening?**
- **Records** are Java's concise way to define data classes
- `DiceRollRequest` - The AI will provide: how many dice and how many sides
- `DiceRollResponse` - We return: individual rolls, total, and a description
- Spring AI automatically converts these to/from JSON for the AI

### 2. Creating a Tool Class with @Tool Annotation

```java
class DiceTools {

    @Tool(description = "Roll dice for D&D game mechanics. Use this for attack rolls, damage, ability checks, or saving throws.")
    DiceRollResponse rollDice(DiceRollRequest request) {
        int[] rolls = new int[request.numberOfDice()];
        int total = 0;

        for (int i = 0; i < request.numberOfDice(); i++) {
            rolls[i] = random.nextInt(request.sides()) + 1;
            total += rolls[i];
        }

        return new DiceRollResponse(rolls, total, description);
    }
}
```

**What's happening?**
- `@Tool` annotation marks this method as callable by the AI
- `description` tells the AI WHEN to use this tool (critical!)
- The method name (`rollDice`) becomes the tool name
- Spring AI automatically handles JSON conversion for input/output

### 3. Registering the Tool with ChatClient

```java
var dungeonMaster = ChatClient.builder(chatModel)
        .defaultSystem("""
                You are a Dungeon Master...
                IMPORTANT: When the player attacks, IMMEDIATELY use
                the rollDice tool to roll 1d20...
                """)
        .defaultTools(new DiceTools())  // <-- Just pass an instance!
        .build();
```

**What's happening?**
- `.defaultTools(new DiceTools())` registers all `@Tool` methods from the class
- The system prompt instructs the AI when to use it
- The AI autonomously decides to call the tool based on context

### 5. The Agentic Loop (What Happens Behind the Scenes)

When you send "I want to attack the goblin":

```
1. USER MESSAGE    → "I want to attack the goblin with my sword!"
                      ↓
2. AI REASONING    → "Attack requires a d20 roll. I should use rollDice."
                      ↓
3. TOOL CALL       → rollDice(numberOfDice=1, sides=20)
                      ↓
4. YOUR CODE RUNS  → Returns: {rolls: [15], total: 15, description: "..."}
                      ↓
5. AI OBSERVES     → "The player rolled 15. That's likely a hit!"
                      ↓
6. AI RESPONDS     → "You rolled a 15! Your sword strikes true..."
```

This is the **agentic pattern**: The AI reasons about the task, decides to use a tool, observes the result, and incorporates it into its response.

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
| Input Record | Define what parameters the tool accepts |
| Output Record | Define what the tool returns |
| Tool Class | A class containing `@Tool` annotated methods |
| `@Tool` annotation | Marks a method as callable by the AI |
| `.defaultTools()` | Register tool class with ChatClient |

### When Does the AI Call a Tool?

The AI decides based on:
1. **Tool description** - "Roll dice for D&D game mechanics"
2. **User context** - "I attack the goblin" implies dice rolling
3. **System prompt** - "You MUST use rollDice for attacks"

## Exercises for Adventurers

### Exercise 1: Add Damage Dice (Easy)

**Quest:** Make the DM roll damage after a successful attack!

**Tasks:**
1. Modify the player message to ask about damage
2. Watch the AI call rollDice multiple times (attack + damage)

**Example message:**
```java
var playerMessage = "I attack the goblin. If I hit, how much damage do I deal with my longsword (1d8)?";
```

### Exercise 2: Create a Coin Flip Tool (Medium)

**Quest:** Add a new tool for simple yes/no decisions!

**Tasks:**
1. Create `CoinFlipRequest` and `CoinFlipResponse` records
2. Create a `flipCoin` function
3. Register it alongside `rollDice`
4. Test with: "Should I go left or right? Flip a coin!"

**Starter code:**
```java
record CoinFlipRequest(String question) {}
record CoinFlipResponse(String result, String answer) {}

// Add this method to your DiceTools class (or create a new tools class)
@Tool(description = "Flip a coin to make a random yes/no decision")
CoinFlipResponse flipCoin(CoinFlipRequest request) {
    var result = random.nextBoolean() ? "HEADS" : "TAILS";
    return new CoinFlipResponse(result, "The coin shows " + result);
}
```

### Exercise 3: Add an Inventory Tool (Hard)

**Quest:** Let the AI check the player's inventory!

**Tasks:**
1. Create a simple inventory map: `Map<String, Integer>`
2. Create `CheckInventoryRequest` and `CheckInventoryResponse`
3. Add logic to check if player has items
4. Test with: "Do I have any health potions?"

### Exercise 4: Multiple Dice Rolls (Hard)

**Quest:** Have a full combat encounter!

**Tasks:**
1. Send a message that triggers multiple tool calls
2. Example: "I attack with my greatsword (1d20 to hit, 2d6 damage), then the goblin counterattacks (1d20 to hit, 1d6 damage)"
3. Watch the AI orchestrate multiple tool calls!

## Comparing to Python (Strands Agents)

The Python equivalent using Strands Agents:

```python
from strands import Agent
from strands_tools import http_request

agent = Agent(tools=[http_request])
agent("Fetch information from this URL...")
```

Our Java equivalent with Spring AI:

```java
class DiceTools {
    @Tool(description = "Roll dice for D&D game mechanics...")
    DiceRollResponse rollDice(DiceRollRequest request) {
        // dice rolling logic
    }
}

var agent = ChatClient.builder(chatModel)
        .defaultTools(new DiceTools())
        .build();

agent.prompt().user("Attack the goblin!").call().content();
```

**Same pattern, different syntax:**
- Define a tool class with `@Tool` annotation
- Register it with the agent
- Let the AI decide when to use it

## Troubleshooting

### "Tool not being called"

**Problem:** The AI responds but doesn't use the dice tool.

**Solutions:**
1. Make the system prompt more explicit about when to use tools
2. Make the user message clearly require dice (e.g., "roll to attack")
3. Check the tool description is clear

### "Invalid tool input"

**Problem:** Spring AI can't parse the AI's tool request.

**Solution:** Ensure your record field names match what Claude sends:
```java
// Good - simple field names
record DiceRollRequest(int numberOfDice, int sides) {}

// Avoid complex nested types for simple tools
```

### "ClassNotFoundException for records"

**Problem:** Java can't find record classes at runtime.

**Solution:** Ensure you're using Java 25 and the records are defined in the same file (for unnamed classes).

## Success Criteria

You've successfully completed Chapter 2 when:

- [ ] The program runs and connects to AWS Bedrock
- [ ] You see "TOOL CALLED" in the output (tool was invoked)
- [ ] The AI incorporates dice results into its narrative
- [ ] You understand how tools/functions work in Spring AI
- [ ] You can explain the agentic loop
- [ ] You've completed at least 1 exercise

## What's Next?

In **Chapter 3: Custom Quests**, you'll learn:
- Creating **custom tools** that interact with external systems
- **Conversation memory** - making the AI remember previous turns
- Building a **multi-turn adventure** with persistent state
- More advanced agentic patterns

Your arsenal grows stronger, adventurer!

---

## Official Documentation

- [Spring AI Function Calling](https://docs.spring.io/spring-ai/reference/api/functions.html) - Tool/Function calling guide
- [AWS Bedrock Tool Use](https://docs.aws.amazon.com/bedrock/latest/userguide/tool-use.html) - Bedrock tool use documentation
- [Spring AI ChatClient](https://docs.spring.io/spring-ai/reference/api/chatclient.html) - ChatClient API reference

## Questions?

If you get stuck:
1. Verify your tool description clearly explains when to use it
2. Check the system prompt instructs the AI to use tools
3. Look for "TOOL CALLED" in the output to confirm tool invocation
4. Review the agentic loop diagram to understand the flow

Remember: A well-equipped adventurer is a successful adventurer!
