# Chapter 1 (JBang Edition): The Art of Agent Summoning

## Your First AI Companion — Zero Setup Required

Welcome, brave adventurer! This is the **JBang edition** of Chapter 1, designed for maximum simplicity. No Maven, no build files, no project structure — just **one Java file** and you're ready to summon your first AI agent.

> **What is JBang?** JBang lets you run Java programs as easily as Python scripts. Dependencies, Java version, and repositories are all declared as comments at the top of your `.java` file. No `pom.xml`, no `build.gradle`, no project setup.

## Learning Objectives

By the end of this chapter, you will be able to:

1. **Create your first AI agent** using Spring AI (without Spring Boot!)
2. **Configure system prompts** to give your agent personality and purpose
3. **Use the ChatClient** to communicate with AI models
4. **Connect to AWS Bedrock** (Claude) for AI inference
5. **Understand modern Java 25 features** (unnamed classes, var, text blocks)

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
- **JBang** - Run Java files directly, no build tool required
- **Spring AI 2.0.0-M2** - AI integration layer (standalone, no Spring Boot)
- **AWS Bedrock** - Claude model hosting

## Prerequisites

Before beginning your summoning ritual, ensure you have:

1. **Java 25 or later** installed
   ```bash
   java -version
   # Should show: java version "25" or higher
   ```

2. **JBang** installed — pick your favorite method:

   ```bash
   # macOS (Homebrew)
   brew install jbangdev/tap/jbang

   # Linux / macOS (SDKMan - recommended)
   sdk install jbang

   # Linux / macOS (curl)
   curl -Ls https://sh.jbang.dev | bash -s - app setup

   # Windows (Scoop)
   scoop install jbang

   # Windows (Chocolatey)
   choco install jbang
   ```

   Verify it works:
   ```bash
   jbang --version
   ```

3. **AWS Account** with Bedrock access
   - AWS credentials configured (via `~/.aws/credentials` or environment variables)
   - Bedrock model access enabled for Claude in your region

4. **Basic Java knowledge**
   - Understanding of variables and methods
   - Familiarity with imports

## How to Run

### One Command — That's It

```bash
jbang DungeonMasterSimple.java
```

JBang will automatically:
- Detect that Java 25+ is required
- Download all Maven dependencies (Spring AI, AWS SDK, SLF4J)
- Resolve the Spring milestones repository
- Compile and run your program

### Alternative: Run as a Script

On macOS/Linux, you can also make the file executable and run it directly:

```bash
chmod +x DungeonMasterSimple.java
./DungeonMasterSimple.java
```

This works because of the shebang line at the top of the file.

### Watch the Magic Happen!

Expected output:
```
[main] INFO DungeonMasterSimple - === Starting Dungeon Master AI Agent ===
[main] INFO DungeonMasterSimple - Player: Hi, I am an adventurer ready for adventure!

[main] INFO DungeonMasterSimple - Dungeon Master says:
[main] INFO DungeonMasterSimple - Greetings, brave soul! I sense great potential within you...
[AI-generated response continues...]
[main] INFO DungeonMasterSimple - === Ending Dungeon Master AI Agent ===
```

## JBang vs Maven — What Changed?

| Aspect | Maven (chapter1) | JBang (this chapter) |
|--------|-------------------|----------------------|
| **Files needed** | `pom.xml` + `mvnw` + `.mvn/` + `.java` | Just the `.java` file |
| **Setup steps** | Download deps, then run | `jbang run` does everything |
| **Dependencies** | Declared in `pom.xml` | `//DEPS` comments in source file |
| **Java version** | Set in `pom.xml` properties | `//JAVA 25+` comment in source file |
| **Custom repos** | `<repositories>` in `pom.xml` | `//REPOS` comment in source file |
| **Run command** | `./mvnw dependency:copy-dependencies ...` then `java --class-path ...` | `jbang DungeonMasterSimple.java` |

The **Java code itself is identical** — only the way you declare dependencies and run the program changes.

## Understanding the JBang Directives

Look at the top of `DungeonMasterSimple.java`:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 25+
//REPOS mavencentral,spring-milestones=https://repo.spring.io/milestone
//DEPS org.springframework.ai:spring-ai-bedrock-converse:2.0.0-M2
//DEPS org.springframework.ai:spring-ai-client-chat:2.0.0-M2
//DEPS software.amazon.awssdk:bedrockruntime:2.41.24
//DEPS software.amazon.awssdk:auth:2.41.24
//DEPS org.slf4j:slf4j-api:2.0.17
//DEPS org.slf4j:slf4j-simple:2.0.17
```

| Directive | Purpose |
|-----------|---------|
| `///usr/bin/env jbang ...` | Shebang line — lets you run the file as `./DungeonMasterSimple.java` |
| `//JAVA 25+` | Requires Java 25 or higher (JBang can auto-download it!) |
| `//REPOS mavencentral,name=url` | Declares Maven repositories — must include `mavencentral` explicitly when adding custom repos |
| `//DEPS group:artifact:version` | Declares a Maven dependency |

## Useful JBang Commands

```bash
# Run the program
jbang DungeonMasterSimple.java

# Edit in your IDE (IntelliJ, VS Code, etc.) with full autocomplete
jbang edit DungeonMasterSimple.java

# Clear JBang's dependency cache
jbang cache clear

# See what JBang resolved
jbang info classpath DungeonMasterSimple.java
```

## What's Next?

In **Chapter 2: AI Agent with Built-in Tools**, you'll learn:
- How to give your AI agent access to community tools
- Using `SmartWebFetchTool` for autonomous web content fetching
- Building an agent that can retrieve and analyze information from the web

The journey has just begun, brave adventurer!
