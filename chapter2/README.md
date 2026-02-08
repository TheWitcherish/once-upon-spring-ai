# Chapter 2 - AI Agent with Built-in Tools

This chapter demonstrates how to equip a Spring AI agent with **SmartWebFetchTool** from the [spring-ai-agent-utils](https://github.com/spring-ai-community/spring-ai-agent-utils) community library, enabling the agent to autonomously fetch and analyze web content.

## Overview

Instead of manually fetching web pages and passing content to the AI, we register `SmartWebFetchTool` as a tool on the `ChatClient`. The agent then decides when and how to use it — fetching the Wikipedia page, converting HTML to Markdown, and summarizing the content on its own.

## How It Works

```java
// Step 1: Create a dedicated ChatClient for the tool's internal summarization
var summarizationClient = ChatClient.builder(chatModel).build();
var webFetchTool = SmartWebFetchTool.builder(summarizationClient)
    .maxContentLength(300_000)
    .build();

// Step 2: Build the agent with SmartWebFetchTool registered as a tool
var agent = ChatClient.builder(chatModel)
    .defaultTools(webFetchTool)
    .build();

// Step 3: Ask the agent — it fetches and analyzes autonomously
var response = agent.prompt()
    .user("Fetch the content from https://en.wikipedia.org/wiki/Dungeons_%26_Dragons "
        + "and tell me the name of the designers of Dungeons and Dragons.")
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

## Running the Example

```bash
./mvnw dependency:copy-dependencies -DoutputDirectory=target/dependency
```

This downloads all required JAR files to `target/dependency/`.

### Step 2: Run Your Program

```bash
java --class-path "target/dependency/*" DungeonMasterWithBuiltInTools.java 2>&1 | grep -v "^WARNING:"
```

## Sample Output

```
[main] INFO DungeonMasterWithBuiltInTools - === Starting AI Agent with Built-in Tools ===
[main] INFO DungeonMasterWithBuiltInTools - Asking AI agent to fetch content from: https://en.wikipedia.org/wiki/Dungeons_%26_Dragons
[main] INFO DungeonMasterWithBuiltInTools - Agent Response:
[main] INFO DungeonMasterWithBuiltInTools - Based on the Wikipedia article, the designers of
Dungeons & Dragons are ...
```