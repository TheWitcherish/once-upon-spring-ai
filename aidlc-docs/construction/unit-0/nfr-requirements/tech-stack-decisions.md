# Tech Stack Decisions - UNIT-0 (Prerequisites)

## Core Framework

### Spring Boot 4.0.1
**Decision**: Use Spring Boot 4.0.1  
**Rationale**: 
- Java 25 support (requires Java 17 minimum, compatible up to Java 25)
- Latest stable release with Spring Framework 7.0.2+
- Best Spring AI compatibility
- Modern Spring ecosystem

### Spring AI (Latest Stable)
**Decision**: Use latest stable Spring AI release  
**Rationale**:
- Most up-to-date features
- Best Bedrock integration
- Active development and bug fixes
- Production-ready stability

## Language Features

### Java 25
**Decision**: Use Java 25 with selective modern features  
**Rationale**: Workshop requirement to showcase Java 25

### Java 25 Features to Use
**Decision**: Records + Text blocks  
**Rationale**:
- **Records**: Perfect for DTOs (request/response objects), reduces boilerplate
- **Text blocks**: Ideal for multi-line prompts and JSON, improves readability
- **Balance**: Simple enough for beginners, modern enough to demonstrate Java 25 benefits
- **Excluded**: Pattern matching, virtual threads (too advanced for Chapter 0)

## LLM Provider

### AWS Bedrock
**Decision**: Amazon Bedrock as exclusive LLM provider  
**Rationale**: Workshop requirement

### Bedrock Model: Claude 4.5 Haiku
**Decision**: Use Claude 4.5 Haiku as default model  
**Rationale**:
- Fast response times (< 5 seconds target)
- Cost-effective for workshop exercises
- Latest Claude generation with improved capabilities
- Sufficient capability for basic interactions

### Bedrock Configuration
- **Region**: Configurable via environment variable (default: us-east-1)
- **Model ID**: `anthropic.claude-4-5-haiku-20250110-v1:0`
- **Temperature**: 0.7 (balanced creativity)
- **Max Tokens**: 1000 (sufficient for workshop responses)

## Build Tool

### Maven 3.9+
**Decision**: Use Maven for build management  
**Rationale**: Workshop requirement, widely adopted, simple for beginners

## Dependencies

### Core Dependencies
```xml
<dependencies>
    <!-- Spring Boot Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring AI Bedrock -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-bedrock-ai-spring-boot-starter</artifactId>
    </dependency>
    
    <!-- AWS SDK Bedrock Runtime -->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>bedrock-runtime</artifactId>
    </dependency>
</dependencies>
```

### Optional Dependencies
- **Lombok**: Not used (keep code explicit for learning)
- **Spring Boot DevTools**: Optional for development convenience

## Configuration Management

### Application Configuration
**Format**: YAML (application.yml)  
**Rationale**: More readable than properties files

### Environment Variables
- `AWS_ACCESS_KEY_ID`: AWS credentials
- `AWS_SECRET_ACCESS_KEY`: AWS credentials
- `AWS_REGION`: Bedrock region (default: us-east-1)

## Logging

### SLF4J with Logback
**Decision**: Use Spring Boot default logging  
**Rationale**: 
- Built-in with Spring Boot
- INFO level for key operations
- No additional configuration needed

## Development Environment

### IDE Support
- **IntelliJ IDEA**: Recommended
- **VS Code**: Supported with Java extensions
- **Rationale**: Most common Java IDEs

### Java Version
- **Required**: Java 25 JDK
- **Verification**: `java -version` in health check

## Deployment

### Local Development Only
**Decision**: No deployment infrastructure  
**Rationale**: Workshop requirement (NFR8)

### Execution
- **Build**: `mvn clean install`
- **Run**: `mvn spring-boot:run`
- **Port**: 8080 (Spring Boot default)

## Excluded Technologies

### Not Used in UNIT-0
- Database (no persistence needed)
- Message queues (no async processing)
- Caching (no performance optimization needed)
- Docker (local execution only)
- CI/CD (workshop focus)
- Monitoring tools (manual testing only)

## Version Summary

| Technology | Version | Rationale |
|------------|---------|-----------|
| Java | 25 | Workshop requirement |
| Spring Boot | 4.0.1 | Java 25 support |
| Spring AI | Latest stable | Best compatibility |
| Maven | 3.9+ | Workshop requirement |
| Bedrock Model | Claude 4.5 Haiku | Fast, cost-effective, latest generation |
| Java Features | Records + Text blocks | Simple but modern |
