# Chapter 2 (JBang Edition): AI Agent with Built-in Tools

## Equipping Your Agent — One File, One Command

This is the **JBang edition** of Chapter 2. It demonstrates how to equip a Spring AI agent with **SmartWebFetchTool** from the [spring-ai-agent-utils](https://github.com/spring-ai-community/spring-ai-agent-utils) community library, enabling the agent to autonomously fetch and analyze web content.

> **Coming from Chapter 1?** You already know the JBang basics (`//DEPS`, `//REPOS`, `//JAVA`). This chapter adds a new concept: giving your AI agent **tools** it can use autonomously.

## Learning Objectives

By the end of this chapter, you will be able to:

1. **Register tools** with a Spring AI ChatClient
2. **Use community tools** like `SmartWebFetchTool` for autonomous web fetching
3. **Understand agentic behavior** — the AI decides when and how to use tools
4. **Build an AI agent** that can retrieve and analyze information from the web

## Overview

Instead of manually fetching web pages and passing content to the AI, we register `SmartWebFetchTool` as a tool on the `ChatClient`. The agent then decides when and how to use it — fetching the Wikipedia page, converting HTML to Markdown, and summarizing the content on its own.

## How It Works

```java
// Step 1: Create a ChatClient for the tool's internal summarization
var agent = ChatClient.builder(chatModel).build();
var webFetchTool = SmartWebFetchTool.builder(agent)
    .maxContentLength(300_000)
    .build();

// Step 2: Ask the agent — it fetches and analyzes autonomously
var response = agent.prompt()
    .user("Using the website https://en.wikipedia.org/wiki/Dungeons_%26_Dragons "
        + "tell me the name of the designers of Dungeons and Dragons.")
    .tools(webFetchTool)
    .call()
    .content();
```

## Key Components

| Component | Description |
|-----------|-------------|
| `spring-ai-agent-utils` | Spring AI Community library providing ready-made agent tools |
| `SmartWebFetchTool` | AI-powered web fetcher with HTML-to-Markdown conversion, caching, and retry |
| `ChatClient` | Spring AI's fluent API for interacting with AI models |

## SmartWebFetchTool Features

- **HTML to Markdown** conversion for clean content extraction
- **AI-powered summarization** using a dedicated ChatClient
- **15-minute TTL caching** to avoid redundant fetches
- **Automatic retry** with exponential backoff on network errors
- **Domain safety checking** enabled by default
- **Configurable content length** (`maxContentLength`) to handle large pages like Wikipedia

## Tech Stack

This chapter uses:
- **Java 25** - Modern Java with unnamed classes (no boilerplate!)
- **JBang** - Run Java files directly, no build tool required
- **Spring AI 2.0.0-M2** - AI integration layer (standalone, no Spring Boot)
- **spring-ai-agent-utils 0.4.2** - Community tools (SmartWebFetchTool)
- **AWS Bedrock** - Claude model hosting

## Prerequisites

Before beginning, ensure you have:

1. **Java 25 or later** installed
   ```bash
   java -version
   # Should show: java version "25" or higher
   ```

2. **JBang** installed (see [chapter1-jbang](../chapter1-jbang/README.md) for installation options)
   ```bash
   jbang --version
   ```

3. **AWS Account** with Bedrock access
   - AWS credentials configured (via `~/.aws/credentials` or environment variables)
   - Bedrock model access enabled for Claude in your region

## How to Run

### One Command — That's It

```bash
jbang DungeonMasterWithBuiltInTools.java
```

### Alternative: Run as a Script

```bash
chmod +x DungeonMasterWithBuiltInTools.java
./DungeonMasterWithBuiltInTools.java
```

### Sample Output

```
[main] INFO DungeonMasterWithBuiltInTools - === Starting AI Agent with Built-in Tools ===
[main] INFO DungeonMasterWithBuiltInTools - Asking AI agent to fetch the name of the designers of Dungeons and Dragons.
[main] INFO DungeonMasterWithBuiltInTools - Agent Response:
[main] INFO DungeonMasterWithBuiltInTools - Based on the Wikipedia article, the designers of
Dungeons & Dragons are ...
```

## What Changed from Chapter 1?

| Aspect | Chapter 1 | Chapter 2 (this chapter) |
|--------|-----------|--------------------------|
| **New dependency** | — | `spring-ai-agent-utils:0.4.2` |
| **Tools** | None — agent only generates text | `SmartWebFetchTool` — agent can fetch web pages |
| **Agent behavior** | Responds from training data only | Autonomously fetches live web content |
| **Tool registration** | — | `.tools(webFetchTool)` on the prompt |

## Understanding the JBang Directives

```java
///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 25+
//REPOS mavencentral,spring-milestones=https://repo.spring.io/milestone
//DEPS org.springframework.ai:spring-ai-bedrock-converse:2.0.0-M2
//DEPS org.springframework.ai:spring-ai-client-chat:2.0.0-M2
//DEPS org.springaicommunity:spring-ai-agent-utils:0.4.2
//DEPS software.amazon.awssdk:bedrockruntime:2.41.24
//DEPS software.amazon.awssdk:auth:2.41.24
//DEPS org.slf4j:slf4j-api:2.0.17
//DEPS org.slf4j:slf4j-simple:2.0.17
```

The only addition compared to Chapter 1 is the `spring-ai-agent-utils` dependency — everything else carries forward.

## Useful JBang Commands

```bash
# Run the program
jbang DungeonMasterWithBuiltInTools.java

# Edit in your IDE with full autocomplete
jbang edit DungeonMasterWithBuiltInTools.java

# See resolved classpath
jbang info classpath DungeonMasterWithBuiltInTools.java
```

## What's Next?

In **Chapter 3: The Adventurer's Arsenal**, you'll learn:
- How to build your own **custom tools** (dice rolling!)
- Using the `@Tool` annotation
- Function calling and the agentic loop

The adventure continues, brave adventurer!
