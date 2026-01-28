# Workshop Application Components

## Overview
The workshop consists of 6 separate Spring Boot applications, one for each chapter. Each application is self-contained with its own configuration, controllers, services, and domain models.

---

## Chapter 0: Prerequisites and Setup

### Component: Chapter0Application
**Purpose**: Starter application to verify environment setup  
**Responsibilities**:
- Validate Java 25, Maven, and IDE setup
- Verify AWS Bedrock connectivity
- Demonstrate basic Spring Boot application structure
- Provide foundation for subsequent chapters

**Key Classes**:
- `Chapter0Application` - Spring Boot main class
- `Chapter0Controller` - REST controller for health checks
- `BedrockConfigProperties` - Configuration properties for Bedrock
- `BedrockHealthService` - Service to verify Bedrock connectivity

---

## Chapter 1: Agent Basics

### Component: Chapter1Application
**Purpose**: Basic Spring AI agent with chat capabilities  
**Responsibilities**:
- Configure Spring AI ChatClient with Bedrock
- Handle simple text prompts and responses
- Manage conversation history
- Demonstrate system prompt customization
- Provide D&D-themed interactions

**Key Classes**:
- `Chapter1Application` - Spring Boot main class
- `Chapter1Controller` - REST controller for agent interactions
- `BedrockChatConfig` - Bedrock ChatClient configuration
- `AgentService` - Service for agent orchestration
- `ConversationMemory` - In-memory conversation history storage
- `ChatRequest` - DTO for incoming chat requests
- `ChatResponse` - DTO for agent responses

---

## Chapter 2: Built-in Tools

### Component: Chapter2Application
**Purpose**: Agent with Spring AI built-in function calling  
**Responsibilities**:
- Register Java methods as agent tools
- Enable function calling capabilities
- Extract function parameters from natural language
- Demonstrate autonomous tool selection
- Provide D&D character stats lookup example

**Key Classes**:
- `Chapter2Application` - Spring Boot main class
- `Chapter2Controller` - REST controller for agent with tools
- `BedrockChatConfig` - Bedrock ChatClient with function calling
- `AgentService` - Service for agent with tool orchestration
- `CharacterStatsFunction` - Built-in function for D&D character stats
- `ConversationMemory` - Conversation history with tool calls
- `ChatRequest` - DTO for chat requests
- `ChatResponse` - DTO for responses with tool usage

---

## Chapter 3: Custom Tools

### Component: Chapter3Application
**Purpose**: Agent with custom D&D dice rolling tool  
**Responsibilities**:
- Create custom tool implementation
- Register custom tools with Spring AI
- Parse dice notation (e.g., "2d6+3")
- Execute dice rolls and return results
- Provide narrative context around rolls

**Key Classes**:
- `Chapter3Application` - Spring Boot main class
- `Chapter3Controller` - REST controller for dice rolling agent
- `BedrockChatConfig` - Bedrock ChatClient with custom tools
- `AgentService` - Service for agent with custom tools
- `DiceRollingTool` - Custom tool for D&D dice mechanics
- `DiceNotationParser` - Parser for dice notation strings
- `DiceRollResult` - Domain model for roll results
- `ConversationMemory` - Conversation history
- `ChatRequest` - DTO for chat requests
- `ChatResponse` - DTO for responses with dice rolls

---

## Chapter 4: MCP Integration

### Component: Chapter4Application
**Purpose**: Agent with Model Context Protocol integration  
**Responsibilities**:
- Set up MCP server connection
- Discover MCP-provided tools
- Integrate external services via MCP
- Handle MCP connection errors
- Provide D&D spell database lookup example

**Key Classes**:
- `Chapter4Application` - Spring Boot main class
- `Chapter4Controller` - REST controller for MCP-enabled agent
- `BedrockChatConfig` - Bedrock ChatClient configuration
- `McpClientConfig` - MCP client configuration
- `McpService` - Service for MCP server communication
- `AgentService` - Service for agent with MCP tools
- `SpellDatabaseTool` - MCP-provided tool for spell lookup
- `ConversationMemory` - Conversation history
- `ChatRequest` - DTO for chat requests
- `ChatResponse` - DTO for responses with MCP tools

---

## Chapter 5: Agent-to-Agent Communication

### Component: Chapter5Application
**Purpose**: Multi-agent system using Google's Agent2Agent (A2A) protocol  
**Responsibilities**:
- Implement Google's A2A protocol (JSON-RPC 2.0 based)
- Create Agent Cards for capability declaration
- Enable standardized agent discovery and task negotiation
- Orchestrate multi-agent D&D scenario using A2A protocol
- Maintain separate contexts per agent

**Key Classes**:
- `Chapter5Application` - Spring Boot main class
- `Chapter5Controller` - REST controller for multi-agent system
- `BedrockChatConfig` - Bedrock ChatClient configuration
- `A2AServer` - A2A protocol server implementation (JSON-RPC 2.0)
- `A2AClient` - A2A protocol client for agent communication
- `AgentCard` - Agent capability declaration (A2A standard)
- `GameMasterAgent` - Agent for game master role with A2A capabilities
- `PlayerAgent` - Agent for player role with A2A capabilities
- `RulesExpertAgent` - Agent for rules consultation with A2A capabilities
- `StorytellerAgent` - Agent for narrative generation with A2A capabilities
- `A2AMessageHandler` - Handler for A2A JSON-RPC messages
- `A2ATaskExecutor` - Executor for A2A task requests
- `AgentRegistry` - Registry of agents with their Agent Cards
- `AgentContext` - Context storage per agent
- `GameScenarioRequest` - DTO for scenario requests
- `GameScenarioResponse` - DTO for multi-agent responses

---

## Common Patterns Across All Chapters

### Configuration Components
Each chapter includes:
- `BedrockConfigProperties` - AWS Bedrock configuration (region, model ID, credentials)
- `BedrockChatConfig` - Spring AI ChatClient bean configuration
- `application.yml` - Spring Boot configuration file

### Controller Components
Each chapter includes:
- REST controller with `/api/chat` endpoint
- Request/response DTOs
- Error handling for agent failures

### Service Components
Each chapter includes:
- `AgentService` - Core agent orchestration logic
- `ConversationMemory` - In-memory conversation history
- Chapter-specific tool/function services

### Domain Models
Each chapter includes:
- Request/response DTOs
- Domain models specific to chapter functionality
- Error response models

---

## Design Principles

1. **Self-Contained**: Each chapter is a complete, runnable Spring Boot application
2. **Progressive Complexity**: Chapters build conceptually but remain independent
3. **Minimal Code**: Focus on core concepts, avoid over-engineering
4. **Clear Separation**: No shared dependencies between chapters
5. **Educational Focus**: Code clarity prioritized over production patterns
