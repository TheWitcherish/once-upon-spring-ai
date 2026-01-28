# Unit of Work Dependencies

## Dependency Matrix

|          | UNIT-0 | UNIT-1 | UNIT-2 | UNIT-3 | UNIT-4 | UNIT-5 |
|----------|--------|--------|--------|--------|--------|--------|
| UNIT-0   | -      | No     | No     | No     | No     | No     |
| UNIT-1   | No     | -      | No     | No     | No     | No     |
| UNIT-2   | No     | No     | -      | No     | No     | No     |
| UNIT-3   | No     | No     | No     | -      | No     | No     |
| UNIT-4   | No     | No     | No     | No     | -      | No     |
| UNIT-5   | No     | No     | No     | No     | No     | -      |

**Result**: Zero dependencies between units - all units are completely independent.

---

## Unit Independence

### UNIT-0: Prerequisites
- **Depends on**: None
- **Depended on by**: None
- **Integration points**: None

### UNIT-1: Agent Basics
- **Depends on**: None
- **Depended on by**: None
- **Integration points**: None

### UNIT-2: Built-in Tools
- **Depends on**: None
- **Depended on by**: None
- **Integration points**: None

### UNIT-3: Custom Tools
- **Depends on**: None
- **Depended on by**: None
- **Integration points**: None

### UNIT-4: MCP Integration
- **Depends on**: None (external MCP server is separate)
- **Depended on by**: None
- **Integration points**: External MCP server (not a unit dependency)

### UNIT-5: A2A Communication
- **Depends on**: None
- **Depended on by**: None
- **Integration points**: None (A2A communication is internal to unit)

---

## Development Sequence

### Recommended Order
1. UNIT-0 (Prerequisites) - Foundation
2. UNIT-1 (Agent Basics) - Core concepts
3. UNIT-2 (Built-in Tools) - Function calling
4. UNIT-3 (Custom Tools) - Tool creation
5. UNIT-4 (MCP Integration) - External services
6. UNIT-5 (A2A Communication) - Multi-agent

### Rationale
- Sequential order ensures progressive learning
- No technical dependencies, only conceptual progression
- Each unit can be developed independently once started
- Order optimizes workshop narrative flow

---

## Build Dependencies

### External Dependencies (All Units)
- Java 25 JDK
- Maven 3.9+
- Spring Boot 3.x
- Spring AI
- AWS SDK for Bedrock

### Unit-Specific External Dependencies
- **UNIT-4**: MCP client library
- **UNIT-5**: JSON-RPC 2.0 library for A2A protocol

### No Internal Dependencies
- Units do not depend on each other
- No shared code modules
- No shared libraries
- Complete isolation
