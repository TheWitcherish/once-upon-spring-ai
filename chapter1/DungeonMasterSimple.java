import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
import org.springframework.ai.bedrock.converse.BedrockChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

private static final Logger log = LoggerFactory.getLogger("DungeonMaster");

void main() {
    log.info("=== Starting Dungeon Master AI Agent ===");

    // Step 1: Create AWS Bedrock Runtime Client
    var bedrockClient = BedrockRuntimeClient.builder()
        .region(Region.EU_CENTRAL_1)
        .credentialsProvider(DefaultCredentialsProvider.builder().build())
        .build();

    // Step 2: Configure model options (which Claude model to use)
    var modelId = "us.anthropic.claude-haiku-4-5-20251001-v1:0";
    var options = BedrockChatOptions.builder()
        .model(modelId)
        .build();

    // Step 3: Create Spring AI ChatModel (wraps Bedrock client)
    var chatModel = BedrockProxyChatModel.builder()
        .bedrockRuntimeClient(bedrockClient)
        .defaultOptions(options)
        .build();

    // Step 4: Build ChatClient with system prompt (defines AI personality)
    var dungeonMaster = ChatClient.builder(chatModel)
        .defaultSystem("""
            You are a Dungeon Master for a Dungeons & Dragons game.
            Create exciting fantasy adventures with vivid details.
            """)
        .build();

    // Step 5: Invoke the AI agent
    var playerMessage = "Hi, I am an adventurer ready for adventure!";
    log.info("Player: " + playerMessage + "\n");

    try {
        var response = dungeonMaster
            .prompt()
            .user(playerMessage)
            .call()
            .content();

        log.info("Dungeon Master says:");
        log.info(response);

    } catch (Exception e) {
        log.error("Error invoking AI agent: {}", e.getMessage());
    }

    log.info("\n=== Ending Dungeon Master AI Agent ===");
}
