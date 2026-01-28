# Workshop Requirements Document

## Intent Analysis

### User Request Summary
Create an educational workshop for students and early developers to explore Agentic AI workflows using Java 25 and Spring AI. The workshop should adapt concepts from the existing Python-based Strands workshop (https://github.com/aws-samples/sample-once-upon-agentic-ai) to Java/Spring AI, focusing on backend implementation with an existing web application available for connection.

### Request Classification
- **Request Type**: New Project (Greenfield)
- **Scope**: Educational workshop with 5 progressive chapters
- **Complexity**: Moderate (adapting proven workshop structure to different technology stack)
- **Duration**: 1-2 hours (quick introduction)
- **Target Audience**: Students and early developers with intermediate Spring Boot knowledge

---

## Functional Requirements

### FR1: Workshop Structure
**Priority**: High

The workshop must follow the same 5-chapter structure as the reference Strands workshop:
1. **Chapter 1: Agent Basics** - Introduction to Spring AI agents and basic concepts
2. **Chapter 2: Built-in Tools** - Using Spring AI's built-in function calling capabilities
3. **Chapter 3: Custom Tools** - Creating custom tools/functions for agents
4. **Chapter 4: MCP Integration** - Model Context Protocol integration with Spring AI
5. **Chapter 5: Agent-to-Agent (A2A)** - Multi-agent coordination and communication

### FR2: D&D Theme Integration
**Priority**: Medium

Maintain the D&D (Dungeons & Dragons) theme from the reference workshop to keep content engaging and fun. Examples should use D&D concepts like:
- Dice rolling mechanics
- Character creation and management
- Game master interactions
- Adventure scenarios

### FR3: Progressive Learning with Exercises
**Priority**: High

Each chapter must provide:
- Starter code with TODO markers for participants to complete
- Clear learning objectives at the beginning
- Hands-on exercises that build upon previous chapters
- Minimal working examples that demonstrate core concepts

### FR4: Amazon Bedrock Integration
**Priority**: High

All LLM interactions must use Amazon Bedrock as the provider:
- Configure Spring AI to use Bedrock client
- Provide clear setup instructions for AWS credentials
- Use appropriate Bedrock models (e.g., Claude, Titan)
- Include error handling for API calls

### FR5: Backend Focus
**Priority**: High

Workshop focuses exclusively on backend Java implementation:
- REST API endpoints for agent interactions
- Service layer for agent orchestration
- No frontend development required
- Documentation for connecting existing web application

### FR6: Spring AI Feature Coverage
**Priority**: High

Demonstrate all major Spring AI capabilities:
- Chat clients and conversation management
- Function calling and tool integration
- Prompt templates and engineering
- Streaming responses
- Memory and context management
- Vector stores and RAG patterns (if time permits)

### FR7: Documentation Format
**Priority**: High

Provide comprehensive Markdown documentation:
- README.md with workshop overview and prerequisites
- Individual chapter READMEs with learning objectives
- Step-by-step instructions for each exercise
- Code comments explaining key concepts
- Troubleshooting guide

---

## Non-Functional Requirements

### NFR1: Technology Stack
**Priority**: High

- **Language**: Java 25 (latest LTS features)
- **Framework**: Spring Boot 3.x with Spring AI
- **Build Tool**: Maven
- **LLM Provider**: Amazon Bedrock
- **Development Environment**: Local development only (no deployment)

### NFR2: Simplicity and Accessibility
**Priority**: High

- Code must be minimal and focused on core concepts
- Avoid over-engineering or production patterns
- Clear separation of concerns
- Self-contained examples that run independently
- No complex infrastructure setup required

### NFR3: Time Constraints
**Priority**: High

Workshop designed for 1-2 hour completion:
- Each chapter: 15-20 minutes
- Quick setup (< 10 minutes)
- Minimal dependencies
- Fast feedback loops

### NFR4: Prerequisites
**Priority**: Medium

Participants should have:
- Intermediate Java knowledge
- Familiarity with Spring Boot basics
- Basic understanding of REST APIs
- AWS account with Bedrock access
- IDE setup (IntelliJ IDEA or VS Code)

### NFR5: Testing Approach
**Priority**: Low

No testing required to keep workshop simple:
- Focus on learning agent concepts
- Manual testing through API calls
- Optional: Provide test examples as reference

### NFR6: Code Quality
**Priority**: Medium

- Follow Java naming conventions
- Use Spring Boot best practices
- Include JavaDoc for public APIs
- Consistent code formatting
- Clear variable and method names

---

## Technical Constraints

### TC1: Java 25 Features
Leverage modern Java features where appropriate:
- Records for DTOs
- Pattern matching
- Text blocks for prompts
- Virtual threads (if beneficial)

### TC2: Spring AI Limitations
Work within Spring AI framework capabilities:
- Use native Spring AI abstractions
- Avoid custom LLM client implementations
- Follow Spring AI patterns for function calling

### TC3: MCP Integration
Model Context Protocol integration must:
- Use Spring AI's MCP support (if available)
- Provide fallback to custom implementation if needed
- Keep integration simple and educational

---

## Success Criteria

### SC1: Learning Outcomes
Participants should be able to:
- Create basic Spring AI agents with chat capabilities
- Implement custom tools/functions for agents
- Integrate multiple agents in a coordinated system
- Understand MCP concepts and integration
- Connect agents to external web applications

### SC2: Completion Rate
- 80%+ of participants complete all 5 chapters within 2 hours
- All code examples run successfully on first attempt
- Clear error messages guide participants when issues occur

### SC3: Code Reusability
- Workshop code serves as reference for future projects
- Examples can be extended for real-world use cases
- Clear patterns demonstrated for common scenarios

---

## Out of Scope

The following are explicitly excluded:
- Frontend development or UI components
- Production deployment or infrastructure setup
- Comprehensive testing strategies
- Performance optimization
- Security hardening
- Database integration
- Authentication/authorization
- Monitoring and observability
- CI/CD pipelines

---

## Dependencies and Assumptions

### Dependencies
- Java 25 JDK installed
- Maven 3.9+ installed
- AWS account with Bedrock access
- AWS credentials configured locally
- Internet connection for Maven dependencies

### Assumptions
- Participants have completed AWS Bedrock setup
- Existing web application is provided separately
- Workshop runs in local development environment
- Participants use modern IDEs with Java support
