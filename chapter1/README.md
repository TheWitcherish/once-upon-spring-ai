# Chapter 1: The Art of Agent Summoning

## Your First AI Companion

Welcome, brave adventurer! In this chapter, you will learn the ancient art of **Agent Summoning** - the ability to call forth an AI companion to assist you on your quests. You'll summon a wise Dungeon Master who will guide you through epic D&D adventures.

## Learning Objectives

By the end of this chapter, you will be able to:

1. **Create your first AI agent** using Spring AI (without Spring Boot!)
2. **Configure system prompts** to give your agent personality and purpose
3. **Use the ChatClient** to communicate with AI models
4. **Connect to AWS Bedrock** (Claude) for AI inference
5. **Understand modern Java features** (unnamed classes, var, text blocks)

## The Quest Ahead

### The Story

You stand before an ancient summoning circle, etched with runes of power. Today, you will perform your first summoning ritual - calling forth a Dungeon Master from the ethereal realm of AI. This wise guide will accompany you on all future adventures, narrating your heroic deeds and presenting challenges for you to overcome.

### What You'll Build

A simple command-line AI agent that:
- Acts as a Dungeon Master for D&D games
- Responds to player actions with creative storytelling
- Maintains character and personality through system prompts

## Tech Stack

This chapter uses:
- **Java 23+** - Modern Java with unnamed classes (no boilerplate!)
- **Spring AI 2.0.0-M1** - AI integration layer (standalone, no Spring Boot)
- **AWS Bedrock** - Claude model hosting

## Prerequisites

Before beginning your summoning ritual, ensure you have:

1. **Java 23 or later** installed
   ```bash
   java -version
   # Should show: java version "23" or higher
   ```

2. **Maven** installed (comes with Maven Wrapper)
   ```bash
   ./mvnw --version
   ```

3. **AWS Account** with Bedrock access
   - AWS credentials configured (via `~/.aws/credentials` or environment variables)
   - Bedrock model access enabled for Claude in your region

4. **Basic Java knowledge**
   - Understanding of variables and methods
   - Familiarity with imports

## How to Run

### Step 1: Download Dependencies

```bash
./mvnw dependency:copy-dependencies -DoutputDirectory=target/dependency
```

This downloads all required JAR files to `target/dependency/`.

### Step 2: Run Your Program

```bash
java --class-path "target/dependency/*" DungeonMasterSimple.java 2>&1 | grep -v "^WARNING:"
```

That's it! No compilation step needed - Java runs the source file directly.

> **Note:** The `2>&1 | grep -v "^WARNING:"` part filters out Netty warnings about deprecated `sun.misc.Unsafe` methods. These warnings are harmless but noisy on Java 22+.

### Step 3: Watch the Magic Happen!

Expected output:
```
========================================
   Welcome to the D&D AI Adventure!
      (Unnamed Class Edition)
========================================

Player: Hello! I am a brave adventurer ready for a quest!

--------------------
Dungeon Master says:
Greetings, brave soul! I sense great potential within you...
[AI-generated response continues...]
```

## Debugging and Testing

### Check Java Version

```bash
java -version
```

Ensure you have Java 23 or higher (unnamed classes became standard in Java 23).

### Verify Dependencies Downloaded

```bash
ls target/dependency/ | head -10
```

You should see JAR files like `spring-ai-bedrock-converse-*.jar`.

### Test AWS Credentials

```bash
aws sts get-caller-identity
```

Should return your AWS account info. If not, configure credentials:
```bash
aws configure
```

### Run with Verbose Output

To see more details during execution:
```bash
java --class-path "target/dependency/*" -Dorg.slf4j.simpleLogger.defaultLogLevel=debug DungeonMasterSimple.java
```

### Check for Compilation Errors Only

To verify your code compiles without running:
```bash
java --class-path "target/dependency/*" --dry-run DungeonMasterSimple.java
```

### Clean and Re-download Dependencies

If something seems wrong with dependencies:
```bash
rm -rf target/dependency
./mvnw dependency:copy-dependencies -DoutputDirectory=target/dependency
```

## Understanding the Code

Let's break down the magical incantations in `DungeonMasterSimple.java`.

### 1. The Unnamed Class

```java
/*
 * No package declaration
 * No class declaration
 * Just imports and a main() method!
 */

import org.springframework.ai.chat.client.ChatClient;
// ... more imports

void main() {
    // Your code goes here
}
```

**What's happening?**
- This is an **unnamed class** - a modern Java feature that eliminates boilerplate
- No `public class MyClass { }` wrapper needed
- No `public static void main(String[] args)` - just `void main()`
- Java runs the file directly as a script!

### 2. Creating the Bedrock Client

```java
var bedrockClient = BedrockRuntimeClient.builder()
    .region(Region.EU_CENTRAL_1)
    .credentialsProvider(DefaultCredentialsProvider.builder().build())
    .build();
```

**What's happening?**
- Creates a connection to AWS Bedrock
- Uses your default AWS credentials (from `~/.aws/credentials` or environment)
- Targets the EU Central region (Frankfurt)

### 3. Configuring Model Options

```java
var options = BedrockChatOptions.builder()
    .model("eu.anthropic.claude-haiku-4-5-20251001-v1:0")
    .build();
```

**What's happening?**
- Specifies which AI model to use (Claude Haiku 4.5)
- Claude Haiku is fast and cost-effective for learning

### 4. Creating the ChatModel

```java
var chatModel = BedrockProxyChatModel.builder()
    .bedrockRuntimeClient(bedrockClient)
    .defaultOptions(options)
    .build();
```

**What's happening?**
- Wraps the AWS client in Spring AI's abstraction layer
- This allows us to use Spring AI's ChatClient interface

### 5. Building the ChatClient with Personality

```java
var dungeonMaster = ChatClient.builder(chatModel)
    .defaultSystem("""
        You are a Dungeon Master for a Dungeons & Dragons game.
        Create exciting fantasy adventures with vivid details.
        Stay in character and keep responses concise (2-3 paragraphs max).
        """)
    .build();
```

**Understanding System Prompts:**
- The **system prompt** is the "personality programming" for your AI
- It tells the AI WHO it is and HOW to behave
- Think of it as the AI's "character sheet" in D&D terms

**Text Blocks ("""):**
- Triple quotes let you write multi-line strings cleanly
- No need for concatenation or escape characters

### 6. Talking to the AI

```java
var response = dungeonMaster
    .prompt()              // Start building a prompt
    .user(playerMessage)   // Add the user's message
    .call()                // Send to AI
    .content();            // Get the text response
```

**The ChatClient Flow:**
1. `prompt()` - Start composing a message
2. `user(message)` - Add the player's question
3. `call()` - Send to AWS Bedrock (Claude model)
4. `content()` - Extract the AI's text response

### 7. The var Keyword

```java
var bedrockClient = BedrockRuntimeClient.builder()...
var options = BedrockChatOptions.builder()...
```

**Type Inference Magic:**
- `var` lets Java figure out the type automatically
- Still strongly typed - Java knows the exact type at compile time
- Makes code cleaner and easier to read

## Exercises for Adventurers

### Exercise 1: Change the Personality (Easy)

**Quest:** Transform your Dungeon Master into a pirate captain!

**Tasks:**
1. Open `DungeonMasterSimple.java`
2. Find the `defaultSystem()` call
3. Change the prompt to:
   ```
   You are a pirate captain leading a crew on treasure hunts.
   Speak like a pirate (arr, matey!).
   Create exciting maritime adventures.
   Keep your responses concise (2-3 paragraphs max).
   ```
4. Change the player message to something pirate-themed
5. Run and see the personality change!

**Learning:** System prompts completely transform AI behavior without changing any code logic.

### Exercise 2: Add More Interactions (Medium)

**Quest:** Have a longer conversation with your AI!

**Tasks:**
1. Add 2-3 more calls to the AI after the existing one
2. Create a simple story flow:
   - Greeting (existing)
   - Explore a location
   - Encounter a challenge
   - Make a decision

**Sample code to add:**
```java
var response2 = dungeonMaster
    .prompt()
    .user("I want to explore the dark cave to the north!")
    .call()
    .content();
System.out.println("\nDungeon Master says:\n" + response2);
```

**Learning:** Each call is independent - the AI doesn't remember previous messages (yet!).

### Exercise 3: Change the Model (Medium)

**Quest:** Try different Claude models!

**Tasks:**
1. Find the `.model()` line in the options builder
2. Try different models:
   - `eu.anthropic.claude-sonnet-4-20250514-v1:0` (smarter, slower)
   - `eu.anthropic.claude-haiku-4-5-20251001-v1:0` (faster, cheaper)
3. Compare response quality and speed

**Learning:** Different models have different capabilities and costs.

### Exercise 4: Create Your Own Agent (Hard)

**Quest:** Summon a completely different AI companion!

**Tasks:**
1. Copy `DungeonMasterSimple.java` to `CodeMentor.java`
2. Change the system prompt to create a programming tutor
3. Ask it programming questions instead of D&D questions

**Example system prompt:**
```
You are a friendly programming mentor specializing in Java.
Explain concepts clearly with examples.
Be patient and supportive.
Keep responses beginner-friendly and concise.
```

**Learning:** One codebase pattern can create many different AI agents!

## Key Concepts Summary

### Unnamed Classes

Modern Java (23+) lets you write simple programs without boilerplate:
```java
// Old way (still works)
public class MyApp {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}

// New way (unnamed class)
void main() {
    System.out.println("Hello");
}
```

### Spring AI ChatClient

The `ChatClient` is your interface to AI models:
```java
ChatClient client = ChatClient.builder(chatModel)
    .defaultSystem("system prompt")  // Set AI personality
    .build();

String response = client
    .prompt()
    .user("user message")
    .call()
    .content();
```

### System vs User Prompts

- **System Prompt:** Instructions for the AI (sets personality and behavior)
- **User Prompt:** The actual question/request from users

## Troubleshooting

### "package does not exist" or "cannot find symbol"

**Problem:** Dependencies not downloaded or classpath wrong.

**Solution:**
```bash
./mvnw dependency:copy-dependencies -DoutputDirectory=target/dependency
java --class-path "target/dependency/*" DungeonMasterSimple.java
```

### "No region found" or "Unable to load region"

**Solution:** Set AWS region environment variable:
```bash
export AWS_REGION=eu-central-1
java --class-path "target/dependency/*" DungeonMasterSimple.java
```

Or modify the code to use a different region in `Region.EU_CENTRAL_1`.

### "Access denied" or "Model not found"

**Solution:** Enable Claude models in AWS Bedrock console:
1. Go to AWS Bedrock console
2. Navigate to "Model access"
3. Request access to Anthropic Claude models
4. Wait for approval (usually instant)

### "Connection timeout" or credential errors

**Solution:** Check AWS credentials:
```bash
aws sts get-caller-identity
```

If that fails, configure credentials:
```bash
aws configure
```

### Netty warnings about sun.misc.Unsafe

These warnings are harmless but noisy:
```
WARNING: A terminally deprecated method in sun.misc.Unsafe has been called
```

This is a known issue with Netty (used by AWS SDK) on Java 22+. To suppress them, filter the output:
```bash
java --class-path "target/dependency/*" DungeonMasterSimple.java 2>&1 | grep -v "^WARNING:"
```

## Success Criteria

You've successfully completed Chapter 1 when:

- [ ] Dependencies download without errors
- [ ] The program runs and connects to AWS Bedrock
- [ ] The Dungeon Master responds with creative D&D content
- [ ] You understand how system prompts work
- [ ] You can modify the AI's personality
- [ ] You've completed at least 1 exercise

## What's Next?

In **Chapter 2: The Adventurer's Arsenal**, you'll learn:
- How to give your AI access to tools and functions
- Making API calls and external integrations
- Creating "agentic" AI that can act on your behalf
- Building a smart adventurer who can check inventory, roll dice, and track stats

The journey has just begun, brave adventurer!

---

## Official Documentation

Essential references for your summoning journey:

- [Spring AI Reference Documentation](https://docs.spring.io/spring-ai/reference/) - Complete Spring AI guide
- [Spring AI ChatClient Guide](https://docs.spring.io/spring-ai/reference/api/chatclient.html) - ChatClient API details
- [AWS Bedrock User Guide](https://docs.aws.amazon.com/bedrock/latest/userguide/) - AWS Bedrock overview
- [AWS Bedrock Converse API](https://docs.aws.amazon.com/bedrock/latest/userguide/conversation-inference.html) - Converse API documentation
- [JEP 477: Implicitly Declared Classes](https://openjdk.org/jeps/477) - Unnamed classes specification

## Questions?

If you get stuck:
1. Check the error messages carefully
2. Review the troubleshooting section
3. Verify your AWS credentials and region
4. Consult the official documentation links above
5. Ask your instructor or workshop facilitator

Remember: Every master summoner started as a novice. Keep practicing!
