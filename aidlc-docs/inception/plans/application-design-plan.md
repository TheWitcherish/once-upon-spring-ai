# Application Design Plan

## Planning Context
This plan defines the high-level component structure for the workshop. The workshop consists of 6 chapters (prerequisites + 5 learning chapters), each demonstrating progressive Spring AI concepts. The design focuses on identifying components, their responsibilities, method signatures, and service layer orchestration.

---

## Design Approach

Based on the workshop's educational nature and chapter-based structure, I recommend organizing components by:
- **Chapter-based modules**: Each chapter as a separate component/package
- **Shared infrastructure**: Common Spring AI configuration and utilities
- **Service layer**: Orchestration services for agent management
- **REST API layer**: Controllers for web application integration

This approach ensures:
- Clear separation between chapters for independent learning
- Reusable infrastructure across all chapters
- Simple service layer for agent orchestration
- Clean API boundaries for external integration

---

## Clarification Questions

Please answer the following questions to guide application design. Fill in your answer after each [Answer]: tag.

### Question 1: Component Organization
How should the workshop chapters be organized as components?

A) Single Spring Boot application with separate packages per chapter
B) Multi-module Maven project with each chapter as a separate module
C) Separate Spring Boot applications per chapter
D) Other (please describe after [Answer]: tag below)

[Answer]: C

### Question 2: Shared Configuration
How should Spring AI and Bedrock configuration be shared across chapters?

A) Single shared configuration class used by all chapters
B) Each chapter has its own configuration (duplicated)
C) Configuration module with chapter-specific overrides
D) Other (please describe after [Answer]: tag below)

[Answer]: B - Please note Bedrock configuration will be the same and AWS credentials (to invoke Bedrock LLMs) will be supplied through the terminal before running the Spring Boot application

### Question 3: REST API Structure
How should REST endpoints be organized?

A) Single controller with endpoints for all chapters (/api/chapter1/..., /api/chapter2/...)
B) Separate controller per chapter (Chapter1Controller, Chapter2Controller, etc.)
C) No REST API (command-line only)
D) Other (please describe after [Answer]: tag below)

[Answer]: B

### Question 4: Agent Management
How should agents be managed across the workshop?

A) Stateless - create new agent instance for each request
B) Stateful - maintain agent instances with conversation history
C) Hybrid - stateless for simple chapters, stateful for complex ones
D) Other (please describe after [Answer]: tag below)

[Answer]: B

### Question 5: Tool/Function Registration
How should custom tools and functions be registered with agents?

A) Global registration - all tools available to all agents
B) Chapter-specific registration - each chapter registers only its tools
C) Dynamic registration - tools registered on-demand
D) Other (please describe after [Answer]: tag below)

[Answer]: B

---

## Application Design Execution Plan

### Phase 1: Component Identification
- [x] Identify main functional components for workshop structure
- [x] Define component responsibilities and boundaries
- [x] Determine shared vs chapter-specific components
- [x] Document component organization approach

### Phase 2: Component Method Definition
- [x] Define method signatures for each component
- [x] Specify input/output types for methods
- [x] Document high-level purpose of each method
- [x] Note: Detailed business rules will be defined later in Functional Design

### Phase 3: Service Layer Design
- [x] Identify orchestration services needed
- [x] Define service responsibilities
- [x] Specify service interactions and patterns
- [x] Document agent lifecycle management approach

### Phase 4: Component Dependency Analysis
- [x] Create dependency matrix showing component relationships
- [x] Define communication patterns between components
- [x] Identify data flow between components
- [x] Document integration points with external systems (Bedrock, web app)

### Phase 5: Design Validation
- [x] Verify all chapters have necessary components
- [x] Ensure design supports progressive learning structure
- [x] Validate component boundaries are clear
- [x] Confirm design is minimal and focused on core concepts

### Phase 6: Documentation Generation
- [x] Generate components.md with component definitions
- [x] Generate component-methods.md with method signatures
- [x] Generate services.md with service definitions
- [x] Generate component-dependency.md with dependency relationships

---

## Instructions for Completion

1. **Answer all questions above** by filling in the [Answer]: tags
2. **Review the execution plan** to ensure it covers all design aspects
3. **Notify me when complete** so I can analyze your answers and proceed

Once all questions are answered, I will:
1. Analyze responses for any ambiguities
2. Ask follow-up questions if needed
3. Request your approval of this plan
4. Execute the plan to generate application design artifacts
