# Once Upon Spring AI: A Developer's Epic Journey into Agentic Java

_"Roll for Initiative... in Java!"_

Welcome, brave adventurer, to the ultimate Spring AI quest! This comprehensive workshop will transform you from a coding apprentice into a master of AI agent orchestration. Through five epic chapters, you'll learn to create, equip, and command digital companions that can think, act, and collaborate like a legendary adventuring party.

## Tech Stack

- **Java 25** - Modern Java with records, unnamed classes, text blocks, and pattern matching
- **JBang** - Zero-setup build tool — runs `.java` files directly with embedded `//DEPS` metadata
- **Spring AI 2.0** - Spring's AI/ML integration framework
- **Spring Boot 4.x** - For MCP Server and A2A agent services (Chapters 4-5)
- **AWS Bedrock** - Cloud-based AI model hosting
- **Claude Haiku 4.5** (Anthropic) - AI model via Bedrock

## The Complete Adventure Map

Your journey through the realms of AI agents is carefully structured as a progressive quest. **Each chapter builds upon the previous one** - complete them in order to unlock the full power of Spring AI!

### [Chapter 1: The Art of Agent Summoning](chapter1/)
**Master the fundamental ritual of agent creation**
- Learn what Spring AI is and how it works
- Summon your first AI companion — a Dungeon Master chatbot
- Configure AWS Bedrock models and system prompts
- Understand the core concepts of agent development

### [Chapter 2: AI Agent with Built-in Tools](chapter2/)
**Equip your agents with community-powered tools**
- Discover Spring AI community tools (`spring-ai-agent-utils`)
- Learn how agents autonomously choose and use tools
- Master web scraping and information gathering with SmartWebFetchTool

### [Chapter 3: The Adventurer's Arsenal](chapter3/)
**Forge your own custom tools and enchantments**
- Transform Java methods into agent tools with `@Tool` and `@ToolParam`
- Create the legendary Dice of Destiny
- Master the `//SOURCES` directive for multi-file JBang projects
- Build domain-specific capabilities

### [Chapter 4: The Tavern Notice Board - MCP Integration](chapter4/)
**Expose tools as remote services through Model Context Protocol**
- Build and deploy an MCP server with Spring Boot
- Create an MCP client with an interactive REPL
- Understand distributed tool architectures over Streamable HTTP
- Master external service connections with `@McpTool`

### [Chapter 5: The Council of Agents - A2A Mastery](chapter5/)
**Command multiple agents in perfect harmony**
- Build a complete multi-agent D&D system
- Master Agent-to-Agent (A2A) communication protocol
- Orchestrate specialized agents (Rules, Character, Game Master) working together
- Combine A2A, MCP, and RAG in a single architecture

## Getting Started

### Prerequisites

1. **Java 25** or higher installed
   ```bash
   java -version
   ```

2. **JBang** installed
   ```bash
   curl -Ls https://sh.jbang.dev | bash -s - app setup
   jbang --version
   ```

3. **AWS credentials** configured with Bedrock access
   ```bash
   aws configure
   ```
   Or set environment variables:
   ```bash
   export AWS_ACCESS_KEY_ID=your-access-key
   export AWS_SECRET_ACCESS_KEY=your-secret-key
   export AWS_REGION=eu-central-1
   ```

### Run Your First Chapter

```bash
cd chapter1
jbang DungeonMasterSimple.java
```

That's it. No Maven, no Gradle, no build files — just one Java file and you're adventuring!

## Project Structure

```
once-upon-spring-ai/
├── README.md
├── AGENTS.md                          # Development guide
├── chapter1/                          # The Art of Agent Summoning
│   └── DungeonMasterSimple.java
├── chapter2/                          # AI Agent with Built-in Tools
│   └── DungeonMasterWithBuiltInTools.java
├── chapter3/                          # The Adventurer's Arsenal
│   ├── DiceTools.java
│   └── DungeonMasterWithCustomTools.java
├── chapter4/                          # The Tavern Notice Board (MCP)
│   ├── DiceRollMcpServer.java
│   ├── DungeonMasterMCPClient.java
│   └── application.properties
└── chapter5/                          # The Council of Agents (A2A)
    ├── agents/
    │   ├── rules/                     # D&D rules lookup agent
    │   ├── character/                 # Character management agent
    │   └── gamemaster/                # Orchestrator agent
    └── utils/
        └── CreateKnowledgeBase.java   # PDF to vector store ingestion
```

## AWS Bedrock Models

Ensure you have enabled the following models in your AWS Bedrock console (`eu-central-1`):

- **Claude Haiku 4.5** (`eu.anthropic.claude-haiku-4-5-20251001-v1:0`) — All chapters
- **Amazon Titan Embed Text V2** — Chapter 5 (vector store embeddings)

## Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [JBang Documentation](https://www.jbang.dev/documentation/guide/latest/)
- [AWS Bedrock Documentation](https://docs.aws.amazon.com/bedrock/)
- [Anthropic Claude Documentation](https://docs.anthropic.com/)
- [A2A Protocol](https://google.github.io/A2A/)
- [Model Context Protocol](https://modelcontextprotocol.io/)

### The Adventure Never Ends...

Remember, the most epic adventures are the ones you create yourself. Whether you're building the next great AI application or just exploring the boundaries of what's possible, you now have the tools and knowledge to make it happen.

_May your agents be wise, your tools be sharp, and your code compile on the first try!_

---

**"The best way to predict the future is to build the agents that will create it."** - Modern Developer Wisdom

_Happy coding, Agent Master!_
