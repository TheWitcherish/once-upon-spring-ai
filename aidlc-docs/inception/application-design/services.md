# Service Layer Design

## Overview
Each chapter application has its own service layer for agent orchestration and business logic. Services are self-contained within each chapter with no cross-chapter dependencies.

---

## Chapter 0: Prerequisites and Setup

### BedrockHealthService
**Responsibility**: Verify AWS Bedrock connectivity and configuration

**Service Interactions**:
- Uses BedrockConfigProperties for configuration
- Directly invokes Bedrock API for health checks
- Returns health status to controller

**Orchestration Pattern**: Simple service with direct API calls

---

## Chapter 1: Agent Basics

### AgentService
**Responsibility**: Orchestrate basic agent interactions with conversation history

**Service Interactions**:
- Uses ChatClient (Spring AI) for agent communication
- Uses ConversationMemory for history management
- Coordinates prompt construction with system prompts
- Returns formatted responses to controller

**Orchestration Pattern**: Service layer with memory management

### ConversationMemory
**Responsibility**: Maintain in-memory conversation history

**Service Interactions**:
- Stores messages in chronological order
- Provides history to AgentService
- Supports clearing history

**Orchestration Pattern**: Simple in-memory storage

---

## Chapter 2: Built-in Tools

### AgentService
**Responsibility**: Orchestrate agent with function calling capabilities

**Service Interactions**:
- Uses ChatClient with function calling enabled
- Uses ConversationMemory for history with tool calls
- Coordinates with CharacterStatsFunction for tool execution
- Returns responses including tool usage information

**Orchestration Pattern**: Service layer with tool coordination

### CharacterStatsFunction
**Responsibility**: Provide D&D character statistics as agent tool

**Service Interactions**:
- Registered with ChatClient as available function
- Invoked autonomously by agent when needed
- Returns structured character data

**Orchestration Pattern**: Tool/function pattern

---

## Chapter 3: Custom Tools

### AgentService
**Responsibility**: Orchestrate agent with custom dice rolling tool

**Service Interactions**:
- Uses ChatClient with custom tool registration
- Uses ConversationMemory for history
- Coordinates with DiceRollingTool for dice mechanics
- Returns responses with dice roll results

**Orchestration Pattern**: Service layer with custom tool integration

### DiceRollingTool
**Responsibility**: Execute D&D dice rolls based on notation

**Service Interactions**:
- Uses DiceNotationParser for parsing dice strings
- Registered with ChatClient as custom function
- Generates random dice roll results
- Returns structured roll results

**Orchestration Pattern**: Custom tool with parser dependency

### DiceNotationParser
**Responsibility**: Parse and validate dice notation strings

**Service Interactions**:
- Used by DiceRollingTool
- Validates notation format
- Extracts dice count, sides, and modifiers

**Orchestration Pattern**: Utility service

---

## Chapter 4: MCP Integration

### AgentService
**Responsibility**: Orchestrate agent with MCP-provided tools

**Service Interactions**:
- Uses ChatClient with MCP tool integration
- Uses ConversationMemory for history
- Coordinates with McpService for external tool access
- Returns responses with MCP tool results

**Orchestration Pattern**: Service layer with external service integration

### McpService
**Responsibility**: Manage MCP server connection and tool discovery

**Service Interactions**:
- Connects to external MCP server
- Discovers available tools from server
- Invokes tools on behalf of agent
- Handles connection errors and retries

**Orchestration Pattern**: External service adapter

### SpellDatabaseTool
**Responsibility**: Provide D&D spell lookup via MCP

**Service Interactions**:
- Uses McpService for MCP communication
- Registered with ChatClient as available function
- Returns spell information from external database

**Orchestration Pattern**: MCP-backed tool

---

## Chapter 5: Agent-to-Agent Communication

### A2AServer
**Responsibility**: Implement Google's A2A protocol server (JSON-RPC 2.0)

**Service Interactions**:
- Handles incoming JSON-RPC 2.0 requests from agents
- Routes requests to appropriate agent handlers
- Maintains registry of agent cards
- Returns JSON-RPC 2.0 responses

**Orchestration Pattern**: JSON-RPC 2.0 server for A2A protocol

### A2AClient
**Responsibility**: Implement Google's A2A protocol client

**Service Interactions**:
- Sends JSON-RPC 2.0 task requests to remote agents
- Discovers agents via agent cards
- Handles A2A protocol communication
- Manages task lifecycle

**Orchestration Pattern**: JSON-RPC 2.0 client for A2A protocol

### AgentCard
**Responsibility**: Declare agent capabilities per A2A standard

**Service Interactions**:
- Defines agent ID, name, capabilities, and endpoint
- Enables agent discovery by capability
- Follows A2A agent card specification
- Serializes to JSON format

**Orchestration Pattern**: A2A capability declaration

### A2AMessageHandler
**Responsibility**: Handle A2A JSON-RPC message processing

**Service Interactions**:
- Validates JSON-RPC 2.0 message format
- Routes method calls to appropriate handlers
- Creates standardized responses and errors
- Ensures A2A protocol compliance

**Orchestration Pattern**: JSON-RPC message handler

### A2ATaskExecutor
**Responsibility**: Execute A2A task requests

**Service Interactions**:
- Processes incoming A2A task requests
- Manages task execution lifecycle
- Tracks task status
- Returns task results in A2A format

**Orchestration Pattern**: Task execution service

### GameMasterAgent
**Responsibility**: Act as game master using A2A protocol

**Service Interactions**:
- Registers with A2AServer using agent card
- Declares capabilities: ["game_master", "scenario_generation", "action_processing"]
- Handles A2A task requests via A2ATaskExecutor
- Sends task requests to other agents via A2AClient
- Uses ChatClient for LLM interactions
- Uses AgentContext for state management

**Orchestration Pattern**: A2A-compliant agent

### PlayerAgent
**Responsibility**: Act as player using A2A protocol

**Service Interactions**:
- Registers with A2AServer using agent card
- Declares capabilities: ["player", "action_decision", "response_generation"]
- Handles A2A task requests via A2ATaskExecutor
- Sends task requests to game master via A2AClient
- Uses ChatClient for LLM interactions
- Uses AgentContext for state management

**Orchestration Pattern**: A2A-compliant agent

### RulesExpertAgent
**Responsibility**: Provide D&D rules clarification using A2A protocol

**Service Interactions**:
- Registers with A2AServer using agent card
- Declares capabilities: ["rules_expert", "rule_clarification", "action_validation"]
- Handles A2A task requests via A2ATaskExecutor
- Provides rules guidance via A2A responses
- Uses ChatClient for LLM interactions
- Uses AgentContext for state management

**Orchestration Pattern**: A2A-compliant agent

### StorytellerAgent
**Responsibility**: Generate narrative descriptions using A2A protocol

**Service Interactions**:
- Registers with A2AServer using agent card
- Declares capabilities: ["storyteller", "narrative_generation", "story_enhancement"]
- Handles A2A task requests via A2ATaskExecutor
- Generates narratives via A2A responses
- Uses ChatClient for LLM interactions
- Uses AgentContext for state management

**Orchestration Pattern**: A2A-compliant agent

### AgentRegistry
**Responsibility**: Maintain registry of agent cards for discovery

**Service Interactions**:
- Stores agent cards from all registered agents
- Enables capability-based agent discovery
- Provides agent card lookup by ID
- Supports dynamic agent registration

**Orchestration Pattern**: Service registry for A2A

### AgentContext
**Responsibility**: Maintain separate context for each agent

**Service Interactions**:
- Stores per-agent state in memory
- Provides context to agents
- Supports context clearing and updates

**Orchestration Pattern**: Context management service

---

## Common Service Patterns

### Configuration Services
All chapters include:
- **BedrockChatConfig**: Creates and configures ChatClient bean
- **BedrockConfigProperties**: Loads configuration from application.yml

### Memory Services
Chapters 1-5 include:
- **ConversationMemory**: In-memory conversation history storage

### Agent Services
All chapters include:
- **AgentService** (or **AgentOrchestrator** for Chapter 5): Core orchestration logic

---

## Service Design Principles

1. **Self-Contained**: Each chapter's services are independent
2. **Stateful Management**: Services maintain conversation state in memory
3. **Clear Responsibilities**: Each service has single, well-defined purpose
4. **Simple Orchestration**: Minimal coordination logic, focus on clarity
5. **Educational Focus**: Service design demonstrates patterns, not production complexity
