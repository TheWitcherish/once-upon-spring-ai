# Chapter 2 - AI Agent with Built-in Tools

This chapter demonstrates Spring AI's built-in web content fetching capability using **Jsoup** (bundled with `spring-ai-jsoup-document-reader`).

## Overview

Spring AI provides the `spring-ai-jsoup-document-reader` module as part of its ETL (Extract-Transform-Load) pipeline. This module includes Jsoup as a transitive dependency, enabling web content fetching out of the box.

## How It Works

```java
// Step 1: Fetch web content using Jsoup (built-in dependency)
var jsoupDoc = Jsoup.connect(wikipediaUrl)
        .userAgent("SpringAI-Agent/1.0")
        .get();

var webContent = jsoupDoc.body().text();

// Step 2: Wrap in Spring AI's Document abstraction
var document = new Document(webContent, Map.of("source", wikipediaUrl));

// Step 3: Pass to AI agent for analysis
var response = agent.prompt()
    .user("Based on this content, tell me the designers...")
    .call()
    .content();
```

## Built-in Capabilities Used

| Component | Description |
|-----------|-------------|
| `spring-ai-jsoup-document-reader` | Spring AI's built-in HTML document reader |
| `Jsoup` | Java HTML parser (transitive dependency) |
| `Document` | Spring AI's document abstraction for AI processing |

## Running the Example

```bash
# Download dependencies
./mvnw dependency:copy-dependencies

# Run with Java 25
java --enable-preview --source 25 \
  --class-path "target/dependency/*" \
  DungeonMasterWithBuiltInTools.java
```

## Sample Output

```
[main] INFO DungeonMasterWithBuiltInTools - === Starting AI Agent with Built-in Tools ===
[main] INFO DungeonMasterWithBuiltInTools - Fetching content from: https://en.wikipedia.org/wiki/Dungeons_%26_Dragons
[main] INFO DungeonMasterWithBuiltInTools - Fetched 151321 characters from Wikipedia
[main] INFO DungeonMasterWithBuiltInTools - Asking AI to extract D&D designers from Wikipedia content...

[main] INFO DungeonMasterWithBuiltInTools - Agent Response:
[main] INFO DungeonMasterWithBuiltInTools - Based on the Wikipedia content provided, the designers of Dungeons & Dragons are:

- **Gary Gygax**
- **Dave Arneson**
```

## Prerequisites

- Java 25+
- AWS credentials configured (for Amazon Bedrock)
- Access to Claude model in eu-central-1 region
