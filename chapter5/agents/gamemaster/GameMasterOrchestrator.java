///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 25+
//SOURCES GameMasterService.java
//SOURCES GameMasterController.java
//REPOS mavencentral,spring-milestones=https://repo.spring.io/milestone
//DEPS org.springframework.boot:spring-boot-starter-web:4.0.2
//DEPS org.springframework.ai:spring-ai-bedrock-converse:2.0.0-M2
//DEPS org.springframework.ai:spring-ai-client-chat:2.0.0-M2
//DEPS org.springframework.ai:spring-ai-mcp:2.0.0-M2
//DEPS io.modelcontextprotocol.sdk:mcp:1.0.0
//DEPS io.github.a2asdk:a2a-java-sdk-client:0.3.3.Final
//DEPS io.github.a2asdk:a2a-java-sdk-client-transport-jsonrpc:0.3.3.Final
//DEPS software.amazon.awssdk:bedrockruntime:2.41.34
//DEPS software.amazon.awssdk:auth:2.41.34
//DEPS com.fasterxml.jackson.core:jackson-databind:2.18.4
//DEPS org.slf4j:slf4j-api:2.0.17
//DEPS org.springframework:spring-core:7.0.3

package com.amazonaws;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpSchema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.bedrock.converse.BedrockChatOptions;
import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

/// Game Master Orchestrator — coordinates Rules Agent, Character Agent via A2A, and MCP Dice Server.
///
/// Architecture:
///   GameMasterOrchestrator (port 8009)
///       ├── Rules Agent      (A2A → port 8000)
///       ├── Character Agent   (A2A → port 8001)
///       └── MCP Dice Server   (MCP → port 8080)
///
/// Run with: jbang GameMasterOrchestrator.java
@SpringBootApplication
public class GameMasterOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(GameMasterOrchestrator.class);

    private static final String SYSTEM_PROMPT = """
        You are a D&D Game Master orchestrator with access to specialized agents and tools.

        Available agents (use sendMessage to communicate with them):
        %s

        To communicate with agents:
        1. Use sendMessage with the exact agent name and a comprehensive task description
        2. Use rollDice for dice rolling (via MCP)

        Available D&D dice types:
        - d4 (4-sided die) — Used for damage rolls of small weapons like daggers
        - d6 (6-sided die) — Used for damage rolls of weapons like shortswords, spell damage
        - d8 (8-sided die) — Used for damage rolls of weapons like longswords, rapiers
        - d10 (10-sided die) — Used for damage rolls of heavy weapons, percentile rolls
        - d12 (12-sided die) — Used for damage rolls of great weapons like greataxes
        - d20 (20-sided die) — Used for ability checks, attack rolls, saving throws
        - d100 (percentile die) — Used for random tables, wild magic surges

        IMPORTANT: Always use the sendMessage tool with the exact agent name. Never invent or guess URLs.

        Be creative, engaging, and use your available tools to enhance the D&D experience.

        When responding, always structure your output as JSON with these fields:
        - "response": Your narrative response as Game Master
        - "actionsSuggestions": A list of 3 suggested actions for the player
        - "details": Brief summary of tools/agents used
        - "diceRolls": A list of dice rolls, each with "diceType", "result", and "reason"
        """;

    public static void main(final String[] args) {
        System.setProperty("server.port", "8009");
        System.setProperty("spring.application.name", "gamemaster-orchestrator");
        System.setProperty("remote.agents.urls", "http://localhost:8000/a2a/,http://localhost:8001/a2a/");
        SpringApplication.run(GameMasterOrchestrator.class, args);
    }

    @Bean
    ChatClient chatClient(BedrockProxyChatModel chatModel,
                           RemoteAgentConnections remoteAgentConnections) {

        String systemPrompt = SYSTEM_PROMPT.formatted(remoteAgentConnections.getAgentDescriptions());

        log.info("Initializing routing ChatClient with agents: {}", remoteAgentConnections.getAgentNames());

        return ChatClient.builder(chatModel)
                .defaultSystem(systemPrompt)
                .build();
    }
}

/// CORS configuration
@Configuration
class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}

/// MCP client connection to the Dice Roll Server from chapter4.
/// Connects lazily — the orchestrator starts even if the dice server is down.
@Configuration
class McpClientConfig {

    private static final Logger log = LoggerFactory.getLogger("McpClientConfig");

    @Bean
    ToolCallback[] mcpTools() {
        try {
            var transport = HttpClientStreamableHttpTransport.builder("http://localhost:8080")
                    .endpoint("/mcp")
                    .build();

            var client = McpClient.sync(transport)
                    .clientInfo(new McpSchema.Implementation("gamemaster-mcp-client", "1.0.0"))
                    .build();

            client.initialize();

            var tools = client.listTools().tools().stream().map(McpSchema.Tool::name).toList();
            log.info("MCP tools discovered: {}", tools);

            return SyncMcpToolCallbackProvider.builder()
                    .mcpClients(client)
                    .build()
                    .getToolCallbacks();
        } catch (Exception e) {
            log.warn("MCP Dice Server not available at http://localhost:8080/mcp — dice rolling disabled");
            log.warn("Start it with: cd ../chapter4 && jbang DiceRollMcpServer.java");
            return new ToolCallback[0];
        }
    }
}

/// Spring AI Bedrock ChatModel configuration
@Configuration
class ChatModelConfig {

    @Bean
    BedrockProxyChatModel chatModel() {
        var bedrockClient = BedrockRuntimeClient.builder()
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .build();

        var options = BedrockChatOptions.builder()
                .model("eu.anthropic.claude-haiku-4-5-20251001-v1:0")
                .build();

        return BedrockProxyChatModel.builder()
                .bedrockRuntimeClient(bedrockClient)
                .defaultOptions(options)
                .build();
    }
}
