# Code Generation Plan - UNIT-0 (Prerequisites)

## Unit Context
- **Unit**: UNIT-0 - Prerequisites and Setup
- **Story**: WORKSHOP-000 - Environment Setup and Prerequisites
- **Purpose**: Verify environment setup and AWS Bedrock connectivity
- **Dependencies**: None (first unit)

## Code Location
- **Workspace Root**: `/Users/arnajean/Documents/TheWitcherish/once-upon-spring-ai`
- **Unit Directory**: `chapter0/`
- **Package**: `com.workshop.chapter0`

---

## Code Generation Steps

### Step 1: Project Structure Setup
- [x] Create `chapter0/` directory in workspace root
- [x] Create Maven `pom.xml` with Spring Boot 3.3.x parent
- [x] Create standard Spring Boot directory structure
- [x] Create `application.yml` with Bedrock configuration placeholders

### Step 2: Configuration Layer
- [x] Create `BedrockConfigProperties.java` (Record) for Bedrock settings
- [x] Create `BedrockChatConfig.java` for ChatClient bean configuration
- [x] Implement environment variable binding for AWS credentials

### Step 3: Model Layer
- [x] Create `HealthResponse.java` (Record) for health check response
- [x] Create `BedrockHealthResponse.java` (Record) for Bedrock test response
- [x] Use Java 25 Records for DTOs

### Step 4: Service Layer
- [x] Create `BedrockHealthService.java`
- [x] Implement `testInvoke()` method with Bedrock call
- [x] Add INFO-level logging

### Step 5: Controller Layer
- [x] Create `Chapter0Controller.java`
- [x] Implement health and bedrock test endpoints
- [x] Add error handling

### Step 6: Main Application Class
- [x] Create `Chapter0Application.java`
- [x] Add @SpringBootApplication

### Step 7: Documentation
- [x] Create README.md with setup instructions

### Step 8: Build Configuration
- [x] Verify `pom.xml` has all required dependencies
- [x] Set Java 25 compiler target
- [x] Configure Spring Boot Maven plugin

---

## Story Traceability
- **WORKSHOP-000**: All steps implement environment setup verification

## Expected Deliverables
1. Complete Spring Boot application in `chapter0/`
2. Health check REST API
3. Bedrock connectivity test API
4. README with setup instructions
5. Working Maven build

## Validation Criteria
- [ ] Application builds with `mvn clean install`
- [ ] Application runs with `mvn spring-boot:run`
- [ ] Health endpoint returns 200 OK
- [ ] Bedrock test endpoint successfully calls Claude 4.5 Haiku
- [ ] README provides clear setup instructions
