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
- **Java 25** - Modern Java with unnamed classes (no boilerplate!)
- **Spring AI 2.0.0-M2** - AI integration layer (standalone, no Spring Boot)
- **AWS Bedrock** - Claude model hosting

## Prerequisites

Before beginning your summoning ritual, ensure you have:

1. **Java 25 or later** installed
   ```bash
   java -version
   # Should show: java version "25" or higher
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

### Step 3: Watch the Magic Happen!

Expected output:
```
[main] INFO DungeonMasterSimple - === Starting Dungeon Master AI Agent ===
[main] INFO DungeonMasterSimple - Player: Hi, I am an adventurer ready for adventure!

[main] INFO DungeonMasterSimple - Dungeon Master says:
[main] INFO DungeonMasterSimple - Greetings, brave soul! I sense great potential within you...
[AI-generated response continues...]
[main] INFO DungeonMasterSimple - === Ending Dungeon Master AI Agent ===
```

## What's Next?

In **Chapter 2: AI Agent with Built-in Tools**, you'll learn:
- How to give your AI agent access to community tools
- Using `SmartWebFetchTool` for autonomous web content fetching
- Building an agent that can retrieve and analyze information from the web

The journey has just begun, brave adventurer!