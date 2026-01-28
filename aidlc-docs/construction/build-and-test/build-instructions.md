# Build Instructions

## Prerequisites
- **Java**: Java 25 JDK
- **Build Tool**: Maven 3.9+
- **IDE**: IntelliJ IDEA or VS Code (optional)
- **AWS**: AWS account with Bedrock access in eu-central-1

## Environment Setup

### 1. Configure AWS Credentials
```bash
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
export AWS_REGION=eu-central-1
```

## Build All Units

### Chapter 0 (Prerequisites)
```bash
cd chapter0
mvn clean install
```

**Expected Output**: BUILD SUCCESS

### Chapters 1-5 (To Be Generated)
Follow the same pattern for each chapter once generated:
```bash
cd chapterX
mvn clean install
```

## Run Individual Chapters

### Chapter 0
```bash
cd chapter0
mvn spring-boot:run
```

Access at: `http://localhost:8080`

### Chapters 1-5
Each chapter runs on port 8080 (run one at a time):
```bash
cd chapterX
mvn spring-boot:run
```

## Troubleshooting

### Build Failures
- Verify Java 25 is installed: `java -version`
- Verify Maven is installed: `mvn -version`
- Clear Maven cache: `mvn clean`

### Runtime Failures
- Check AWS credentials are set
- Verify Bedrock access in eu-central-1
- Check application logs for errors
