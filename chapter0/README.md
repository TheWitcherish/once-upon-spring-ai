# Chapter 0: Prerequisites and Setup

## Learning Objectives
- Verify Java 25, Maven, and IDE setup
- Test AWS Bedrock connectivity
- Understand basic Spring Boot + Spring AI structure
- Confirm environment is ready for workshop

## Prerequisites
- Java 25 JDK installed
- Maven 3.9+ installed
- IDE (IntelliJ IDEA or VS Code with Java extensions)
- AWS account with Bedrock access
- AWS credentials configured

## Setup Instructions

### 1. Verify Java 25
```bash
java -version
# Should show Java 25
```

### 2. Verify Maven
```bash
mvn -version
# Should show Maven 3.9+
```

### 3. Configure AWS Credentials
Set environment variables:
```bash
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
export AWS_REGION=eu-central-1
```

### 4. Build the Project
```bash
cd chapter0
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Health Check
```bash
curl http://localhost:8080/api/health
```

**Response:**
```json
{
  "status": "UP",
  "message": "Chapter 0 is running"
}
```

### Bedrock Connectivity Test
```bash
curl http://localhost:8080/api/bedrock/test
```

**Response:**
```json
{
  "connected": true,
  "model": "anthropic.claude-4-5-haiku-20250110-v1:0",
  "testResponse": "Hello from Claude! ..."
}
```

## Troubleshooting

### Issue: Java version mismatch
**Solution**: Ensure Java 25 is installed and set as default

### Issue: AWS credentials not found
**Solution**: Verify environment variables are set correctly

### Issue: Bedrock access denied
**Solution**: Ensure your AWS account has Bedrock access enabled in eu-central-1

### Issue: Model not found
**Solution**: Verify Claude 4.5 Haiku is available in your region

## What's Next?
Once both endpoints return successful responses, you're ready for Chapter 1: Agent Basics!
