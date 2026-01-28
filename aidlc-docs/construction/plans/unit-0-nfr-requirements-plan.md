# NFR Requirements Plan - UNIT-0 (Prerequisites)

## Context
This plan assesses non-functional requirements and tech stack decisions for UNIT-0: Prerequisites and Setup chapter.

**Unit Purpose**: Environment setup verification and AWS Bedrock connectivity testing

---

## NFR Assessment Questions

### Question 1: Spring Boot Version
Which Spring Boot version should be used?

A) Spring Boot 3.2.x (stable, widely adopted)
B) Spring Boot 3.3.x (latest stable with newest features)
C) Spring Boot 3.4.x (cutting edge, may have compatibility issues)
D) Other (please specify after [Answer]: tag below)

[Answer]: B

### Question 2: Spring AI Version
Which Spring AI version should be used?

A) Latest stable release
B) Specific version (please specify in answer)
C) Snapshot/milestone version for newest features
D) Other (please specify after [Answer]: tag below)

[Answer]: A

### Question 3: Java 25 Features Usage
Which Java 25 features should be demonstrated?

A) Records for DTOs only
B) Records + Pattern matching
C) Records + Pattern matching + Text blocks + Virtual threads
D) Minimal Java features (keep simple for beginners)
E) Other (please specify after [Answer]: tag below)

[Answer]: D

---

## Follow-up Clarification Questions

### Clarification Question 1: Java 25 Features Specificity
You mentioned "Minimal Java features but showcase cool Java 25 features" - this is somewhat contradictory. Please clarify the specific approach:

A) Use only Records for DTOs (minimal but modern)
B) Use Records + Text blocks for prompts (2 features, still simple)
C) Use Records + Text blocks + Pattern matching (3 features, moderate)
D) Use Records + Text blocks + Pattern matching + one other Java 25 feature (please specify which)

[Answer]: B

### Question 4: AWS Bedrock Model
Which Bedrock model should be the default?

A) Claude 3.5 Sonnet (balanced performance and cost)
B) Claude 3 Opus (highest capability)
C) Claude 3 Haiku (fastest and cheapest)
D) Titan models
E) Other (please specify after [Answer]: tag below)

[Answer]: E - Claude 4.5 Haiku on Amazon Bedrock

### Question 5: Response Time Expectations
What are acceptable response times for workshop exercises?

A) < 2 seconds (fast, good UX)
B) < 5 seconds (acceptable for learning)
C) < 10 seconds (acceptable, focus on concepts not speed)
D) No specific requirement
E) Other (please specify after [Answer]: tag below)

[Answer]: B

### Question 6: Error Handling Approach
How detailed should error handling be?

A) Minimal - basic try/catch with simple messages
B) Standard - structured error responses with HTTP status codes
C) Comprehensive - detailed error messages, logging, retry logic
D) Other (please specify after [Answer]: tag below)

[Answer]: B

### Question 7: Logging Level
What logging approach should be used?

A) Minimal - errors only
B) Standard - info level for key operations
C) Verbose - debug level for learning purposes
D) Other (please specify after [Answer]: tag below)

[Answer]: B

---

## Execution Plan

### Phase 1: Tech Stack Selection
- [ ] Determine Spring Boot version
- [ ] Determine Spring AI version
- [ ] Determine Java 25 feature usage
- [ ] Determine AWS Bedrock model
- [ ] Document dependency versions

### Phase 2: Performance Requirements
- [ ] Define response time expectations
- [ ] Determine throughput requirements (if any)
- [ ] Assess resource constraints

### Phase 3: Reliability Requirements
- [ ] Define error handling approach
- [ ] Determine logging strategy
- [ ] Assess monitoring needs (if any)

### Phase 4: Security Requirements
- [ ] Define AWS credentials management approach
- [ ] Determine API security (if any)
- [ ] Assess data protection needs

### Phase 5: Documentation
- [ ] Generate nfr-requirements.md
- [ ] Generate tech-stack-decisions.md
- [ ] Document rationale for decisions

---

## Instructions
1. Answer all questions above
2. Notify me when complete
3. I will analyze for ambiguities
4. Generate NFR artifacts upon approval
