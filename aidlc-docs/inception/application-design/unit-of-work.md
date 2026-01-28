# Units of Work

## Overview
The workshop is decomposed into 6 independent units of work, each representing a complete Spring Boot application for one chapter.

---

## UNIT-0: Prerequisites and Setup
**Purpose**: Environment setup and verification  
**Scope**: Chapter 0 - Prerequisites  
**Story**: WORKSHOP-000

**Responsibilities**:
- Validate Java 25, Maven, IDE setup
- Verify AWS Bedrock connectivity
- Provide starter Spring Boot application
- Demonstrate basic project structure

**Deliverables**:
- Spring Boot application with health check endpoints
- Bedrock connectivity test
- README with setup instructions

---

## UNIT-1: Agent Basics
**Purpose**: Basic Spring AI agent with chat capabilities  
**Scope**: Chapter 1 - Agent Basics  
**Story**: WORKSHOP-001

**Responsibilities**:
- Configure Spring AI ChatClient with Bedrock
- Implement conversation memory
- Handle basic prompts and responses
- Demonstrate D&D-themed interactions

**Deliverables**:
- Spring Boot application with agent service
- REST API for chat interactions
- Conversation history management
- README with learning objectives

---

## UNIT-2: Built-in Tools
**Purpose**: Agent with Spring AI function calling  
**Scope**: Chapter 2 - Built-in Tools  
**Story**: WORKSHOP-002

**Responsibilities**:
- Register Java methods as agent tools
- Enable function calling capabilities
- Demonstrate autonomous tool selection
- Provide D&D character stats example

**Deliverables**:
- Spring Boot application with function calling
- Character stats tool implementation
- REST API with tool usage
- README with tool concepts

---

## UNIT-3: Custom Tools
**Purpose**: Agent with custom D&D dice rolling tool  
**Scope**: Chapter 3 - Custom Tools  
**Story**: WORKSHOP-003

**Responsibilities**:
- Create custom dice rolling tool
- Parse dice notation (e.g., "2d6+3")
- Execute dice rolls with results
- Provide narrative context

**Deliverables**:
- Spring Boot application with custom tool
- Dice rolling implementation
- Dice notation parser
- REST API with dice functionality
- README with custom tool patterns

---

## UNIT-4: MCP Integration
**Purpose**: Agent with Model Context Protocol integration  
**Scope**: Chapter 4 - MCP Integration  
**Story**: WORKSHOP-004

**Responsibilities**:
- Set up MCP server connection
- Discover MCP-provided tools
- Integrate external services via MCP
- Provide D&D spell database example

**Deliverables**:
- Spring Boot application with MCP client
- MCP service implementation
- Spell database tool via MCP
- REST API with MCP tools
- README with MCP concepts

---

## UNIT-5: Agent-to-Agent Communication
**Purpose**: Multi-agent system with Google's A2A protocol  
**Scope**: Chapter 5 - A2A Communication  
**Story**: WORKSHOP-005

**Responsibilities**:
- Implement Google's A2A protocol (JSON-RPC 2.0)
- Create Agent Cards for capability declaration
- Enable agent discovery and task negotiation
- Orchestrate 4 specialized agents (Game Master, Player, Rules Expert, Storyteller)

**Deliverables**:
- Spring Boot application with A2A server/client
- 4 specialized agents with Agent Cards
- A2A message handler and task executor
- REST API for multi-agent scenarios
- README with A2A protocol concepts

---

## Code Organization

### Directory Structure
```
workspace-root/
├── chapter0/          # UNIT-0: Prerequisites
├── chapter1/          # UNIT-1: Agent Basics
├── chapter2/          # UNIT-2: Built-in Tools
├── chapter3/          # UNIT-3: Custom Tools
├── chapter4/          # UNIT-4: MCP Integration
├── chapter5/          # UNIT-5: A2A Communication
└── README.md          # Workshop overview
```

### Per-Unit Structure
Each chapter follows standard Spring Boot structure:
```
chapterX/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/workshop/chapterX/
│   │   │       ├── ChapterXApplication.java
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── config/
│   │   │       └── model/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── pom.xml
└── README.md
```

### Package Naming
- Base package: `com.workshop.chapterX`
- Controllers: `com.workshop.chapterX.controller`
- Services: `com.workshop.chapterX.service`
- Configuration: `com.workshop.chapterX.config`
- Models: `com.workshop.chapterX.model`

---

## Development Approach

### Sequential Development
Units developed in order: UNIT-0 → UNIT-1 → UNIT-2 → UNIT-3 → UNIT-4 → UNIT-5

**Rationale**:
- Ensures progressive learning complexity
- Each chapter builds conceptually on previous
- Easier to maintain consistency
- Better for workshop narrative flow

### Independence
- No code sharing between units
- Each unit has own dependencies
- Duplicated configuration acceptable
- Complete isolation for learning clarity

### Build and Run
Each unit independently:
- Builds with `mvn clean install`
- Runs with `mvn spring-boot:run`
- Tests with `mvn test`
- No cross-unit dependencies
