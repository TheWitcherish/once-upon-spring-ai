# Once Upon Spring AI

A hands-on workshop series teaching developers how to build AI agents using **Java** and **Spring AI** with **AWS Bedrock**.

## Overview

This workshop uses D&D (Dungeons & Dragons) storytelling as a creative theme to explore AI agent development. Each chapter progressively introduces new concepts, from basic chat interactions to advanced agent architectures.

## Tech Stack

- **Java 23+** - Modern Java with features like unnamed classes, var keyword, and text blocks
- **Spring AI 2.0** - Spring's AI/ML integration framework
- **AWS Bedrock** - Cloud-based AI model hosting
- **Claude (Anthropic)** - AI models via Bedrock

## Prerequisites

- Java 23 or higher
- AWS account with Bedrock access enabled
- AWS credentials configured (`~/.aws/credentials` or environment variables)
- Maven (included via Maven Wrapper)

## Workshop Chapters

| Chapter | Title | Description |
|---------|-------|-------------|
| 1 | The Art of Agent Summoning | Create your first AI agent - a Dungeon Master chatbot |

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd once-upon-spring-ai
```

### 2. Configure AWS credentials

Ensure your AWS credentials are set up with access to Bedrock:

```bash
aws configure
```

Or set environment variables:

```bash
export AWS_ACCESS_KEY_ID=your-access-key
export AWS_SECRET_ACCESS_KEY=your-secret-key
export AWS_REGION=eu-central-1
```

### 3. Run a chapter

Navigate to a chapter directory and follow its README:

```bash
cd chapter1
./mvnw dependency:copy-dependencies -DoutputDirectory=target/dependency
java --class-path "target/dependency/*" DungeonMasterSimple.java
```

## Project Structure

```
once-upon-spring-ai/
├── README.md           # This file
├── chapter1/           # The Art of Agent Summoning
│   ├── README.md       # Chapter-specific instructions
│   ├── pom.xml         # Maven dependencies
│   └── *.java          # Source files
└── ...                 # Future chapters
```

## Learning Path

Each chapter builds upon the previous one:

1. **Chapter 1** - Learn the basics of Spring AI ChatClient and AWS Bedrock integration
2. **Future chapters** - Memory, tools, RAG, multi-agent systems, and more

## AWS Bedrock Models

This workshop uses Claude models via AWS Bedrock. Ensure you have enabled the following models in your AWS console:

- Claude Haiku 4.5 (`eu.anthropic.claude-haiku-4-5-20251001-v1:0`)

## Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [AWS Bedrock Documentation](https://docs.aws.amazon.com/bedrock/)
- [Anthropic Claude Documentation](https://docs.anthropic.com/)

## License

This workshop is provided for educational purposes.
