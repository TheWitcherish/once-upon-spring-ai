import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
import org.springframework.ai.bedrock.converse.BedrockChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

import org.springaicommunity.agent.tools.SmartWebFetchTool;

private static final Logger log = LoggerFactory.getLogger("DungeonMasterWithBuiltInTools");

void main() {
    log.info("=== Starting AI Agent with Built-in Tools ===");

    // Step 1: Create AWS Bedrock Runtime Client
    var bedrockClient = BedrockRuntimeClient.builder()
        .region(Region.EU_CENTRAL_1)
        .credentialsProvider(DefaultCredentialsProvider.builder().build())
        .build();

    // Step 2: Configure model options
    var modelId = "global.anthropic.claude-haiku-4-5-20251001-v1:0";
    var options = BedrockChatOptions.builder()
        .model(modelId)
        .build();

    // Step 3: Create Spring AI ChatModel
    var chatModel = BedrockProxyChatModel.builder()
        .bedrockRuntimeClient(bedrockClient)
        .defaultOptions(options)
        .build();

    // Step 4: Create SmartWebFetchTool tool with its own ChatClient to capture the relevant content
    var summarizationClient = ChatClient.builder(chatModel).build();
    var webFetchTool = SmartWebFetchTool.builder(summarizationClient)
        .maxContentLength(300_000)
        .build();

    // Step 5: Build an AI Agent with SmartWebFetchTool tool
    var agent = ChatClient.builder(chatModel)
        .defaultTools(webFetchTool)
        .build();

    // Step 6: Ask the AI to fetch and extract information from Wikipedia
    var wikipediaUrl = "https://en.wikipedia.org/wiki/Dungeons_%26_Dragons";
    log.info("Asking AI agent to fetch content from: {}", wikipediaUrl);

    var userMessage = """
        Fetch the content from %s and tell me the name of the designers of Dungeons and Dragons.
        """.formatted(wikipediaUrl);

    try {
        var response = agent.prompt()
            .user(userMessage)
            .call()
            .content();

        log.info("Agent Response:");
        log.info(response);
    } catch (Exception e) {
        log.error("Error invoking AI agent: {}", e.getMessage());
    }

    log.info("\n=== Ending AI Agent with Built-in Tools ===");
}
