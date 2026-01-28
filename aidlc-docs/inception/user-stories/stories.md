# Workshop User Stories

## Chapter 0: Prerequisites and Setup

### Story ID: WORKSHOP-000
**Title**: Environment Setup and Prerequisites  
**Persona**: Alex (Student) / Jordan (Early Developer)  
**Chapter**: Chapter 0 - Prerequisites  
**Priority**: High

**User Story**:  
As a workshop participant,  
I want to set up my development environment with Java 25, Maven, and AWS Bedrock access,  
So that I can complete all workshop exercises without technical blockers.

**Acceptance Criteria**:
- [ ] Java 25 JDK is installed and verified
- [ ] Maven 3.9+ is installed and configured
- [ ] IDE (IntelliJ IDEA or VS Code) is set up with Java support
- [ ] AWS account is created with Bedrock access enabled
- [ ] AWS credentials are configured locally
- [ ] Starter project builds successfully with `mvn clean install`
- [ ] Basic Spring Boot application runs on localhost

**Technical Notes**:
- Provide clear installation instructions for all platforms (Windows, macOS, Linux)
- Include AWS Bedrock model access verification steps
- Starter project should include minimal Spring Boot + Spring AI dependencies

---

## Chapter 1: Agent Basics

### Story ID: WORKSHOP-001
**Title**: Create and Interact with Basic Spring AI Agent  
**Persona**: Alex (Student) / Jordan (Early Developer)  
**Chapter**: Chapter 1 - Agent Basics  
**Priority**: High

**User Story**:  
As a workshop participant,  
I want to create a basic Spring AI agent that can respond to prompts using Amazon Bedrock,  
So that I understand fundamental agent concepts and Spring AI chat client usage.

**Acceptance Criteria**:
- [ ] Spring AI chat client is configured with Amazon Bedrock
- [ ] Agent responds to simple text prompts with coherent answers
- [ ] System prompts can be customized to set agent behavior
- [ ] Conversation history is maintained across multiple interactions
- [ ] Agent demonstrates understanding of D&D context when prompted
- [ ] Code runs without errors and produces expected output

**Technical Notes**:
- Use Spring AI's ChatClient abstraction
- Configure Bedrock with appropriate model (e.g., Claude)
- Demonstrate basic prompt engineering techniques

**Related Stories**: WORKSHOP-000

---

## Chapter 2: Built-in Tools

### Story ID: WORKSHOP-002
**Title**: Use Spring AI Built-in Function Calling  
**Persona**: Alex (Student) / Jordan (Early Developer)  
**Chapter**: Chapter 2 - Built-in Tools  
**Priority**: High

**User Story**:  
As a workshop participant,  
I want to enable my agent to use Spring AI's built-in function calling capabilities,  
So that the agent can perform actions beyond text generation.

**Acceptance Criteria**:
- [ ] Agent can call Java methods as tools/functions
- [ ] Function parameters are correctly extracted from natural language
- [ ] Function results are incorporated into agent responses
- [ ] Multiple functions can be registered and used by the agent
- [ ] Agent autonomously decides when to use functions
- [ ] D&D-themed examples work (e.g., character stats lookup)

**Technical Notes**:
- Use Spring AI's @Tool annotation or function registration
- Demonstrate function calling with simple examples
- Show how agent chooses appropriate functions

**Related Stories**: WORKSHOP-001

---

## Chapter 3: Custom Tools

### Story ID: WORKSHOP-003
**Title**: Create Custom D&D Dice Rolling Tool  
**Persona**: Alex (Student) / Jordan (Early Developer)  
**Chapter**: Chapter 3 - Custom Tools  
**Priority**: High

**User Story**:  
As a workshop participant,  
I want to create a custom dice rolling tool for D&D gameplay,  
So that I understand how to extend agent capabilities with domain-specific functions.

**Acceptance Criteria**:
- [ ] Custom dice rolling function is implemented (supports d4, d6, d8, d10, d12, d20)
- [ ] Function is properly annotated and registered with Spring AI
- [ ] Agent can parse dice notation (e.g., "roll 2d6+3")
- [ ] Dice roll results are returned to agent and user
- [ ] Agent provides narrative context around dice rolls
- [ ] Multiple dice rolls can be performed in single interaction

**Technical Notes**:
- Implement dice rolling logic in Java
- Use proper function documentation for agent understanding
- Demonstrate tool creation pattern for custom capabilities

**Related Stories**: WORKSHOP-002

---

## Chapter 4: MCP Integration

### Story ID: WORKSHOP-004
**Title**: Integrate Model Context Protocol with Spring AI  
**Persona**: Jordan (Early Developer)  
**Chapter**: Chapter 4 - MCP Integration  
**Priority**: Medium

**User Story**:  
As a workshop participant,  
I want to integrate external services using Model Context Protocol,  
So that my agent can access capabilities beyond the local application.

**Acceptance Criteria**:
- [ ] MCP server is set up and running
- [ ] Spring AI agent connects to MCP server
- [ ] Agent can discover and use MCP-provided tools
- [ ] External service calls work through MCP protocol
- [ ] Error handling for MCP connection issues is demonstrated
- [ ] D&D-themed MCP service example works (e.g., spell database lookup)

**Technical Notes**:
- Use Spring AI's MCP support or implement custom integration
- Provide simple MCP server example
- Keep MCP complexity minimal for learning purposes

**Related Stories**: WORKSHOP-003

---

## Chapter 5: Agent-to-Agent Communication

### Story ID: WORKSHOP-005
**Title**: Build Multi-Agent D&D System  
**Persona**: Jordan (Early Developer)  
**Chapter**: Chapter 5 - Agent-to-Agent (A2A)  
**Priority**: High

**User Story**:  
As a workshop participant,  
I want to create a multi-agent system with 3-4 specialized agents that coordinate to run a D&D game,  
So that I understand agent orchestration patterns and inter-agent communication.

**Acceptance Criteria**:
- [ ] 3-4 specialized agents are created (e.g., Game Master, Rules Expert, Character Creator)
- [ ] Agents can communicate and coordinate with each other
- [ ] Agent orchestration pattern is implemented (coordinator or peer-to-peer)
- [ ] Each agent has distinct role and capabilities
- [ ] Multi-agent D&D scenario runs successfully from start to finish
- [ ] Agent interactions demonstrate proper coordination and context sharing

**Technical Notes**:
- Implement agent coordination using Spring AI patterns
- Show how agents maintain separate contexts
- Demonstrate message passing between agents
- Keep orchestration logic simple but functional

**Related Stories**: WORKSHOP-004

---

## Story Summary

**Total Stories**: 6 (1 prerequisite + 5 chapter stories)

**Story Organization**: By chapter progression (0 → 1 → 2 → 3 → 4 → 5)

**Personas Covered**:
- Alex (Student): All stories
- Jordan (Early Developer): All stories, with emphasis on Chapters 4-5

**INVEST Criteria Validation**:
- ✅ **Independent**: Each story can be completed independently
- ✅ **Negotiable**: Implementation details flexible within acceptance criteria
- ✅ **Valuable**: Each story delivers learning value to participants
- ✅ **Estimable**: Stories are scoped to 15-20 minutes each
- ✅ **Small**: High-level stories appropriate for 1-2 hour workshop
- ✅ **Testable**: Clear acceptance criteria enable validation
