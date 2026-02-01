import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
import org.springframework.ai.bedrock.converse.BedrockChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

import java.util.Random;

DungeonMasterWithCustomToolsogger log = LoggerFactory.getLogger("DungeonMaster");
private static final Random random = new Random();

// Record for dice roll input
record DiceRollRequest(int numberOfDice, int sides) {}

// Record for dice roll output
record DiceRollResponse(int[] rolls, int total, String description) {}

// Our tool class - contains methods the AI can call
class DiceTools {

    @Tool(description = "Roll dice for D&D game mechanics. Use this for attack rolls, damage, ability checks, or saving throws.")
    DiceRollResponse rollDice(DiceRollRequest request) {
        int[] rolls = new int[request.numberOfDice()];
        int total = 0;

        for (int i = 0; i < request.numberOfDice(); i++) {
            rolls[i] = random.nextInt(request.sides()) + 1;
            total += rolls[i];
        }

        var description = String.format("Rolled %dd%d: %s = %d",
                request.numberOfDice(),
                request.sides(),
                java.util.Arrays.toString(rolls),
                total);

        log.info("TOOL CALLED: " + description);

        return new DiceRollResponse(rolls, total, description);
    }
}

void main() {
    log.info("=== Starting Dungeon Master AI Agent with Tools ===");

    // Step 1: Create AWS Bedrock Runtime Client
    var bedrockClient = BedrockRuntimeClient.builder()
            .region(Region.EU_CENTRAL_1)
            .credentialsProvider(DefaultCredentialsProvider.builder().build())
            .build();

    // Step 2: Configure model options
    var modelId = "eu.anthropic.claude-haiku-4-5-20251001-v1:0";
    var options = BedrockChatOptions.builder()
            .model(modelId)
            .build();

    // Step 3: Create Spring AI ChatModel
    var chatModel = BedrockProxyChatModel.builder()
            .bedrockRuntimeClient(bedrockClient)
            .defaultOptions(options)
            .build();

    // Step 4: Build ChatClient with system prompt AND the tools
    var dungeonMaster = ChatClient.builder(chatModel)
            .defaultSystem("""
                    You are a Dungeon Master for a Dungeons & Dragons game.
                    Create exciting fantasy adventures with vivid details.

                    IMPORTANT RULES:
                    - When the player attacks, IMMEDIATELY use the rollDice tool to roll 1d20.
                    - Do NOT ask for stats or AC - assume standard values (AC 13 for goblins).
                    - A roll of 13+ is a hit. On a hit, roll damage dice too.
                    - ALWAYS use the tool first, then narrate based on the result.
                    - Be dramatic and descriptive when narrating combat!
                    """)
            .defaultTools(new DiceTools())  // Simply pass an instance of the tool class!
            .build();

    // Step 5: Invoke the AI agent with a request that requires dice rolling
    var playerMessage = "I want to attack the goblin with my sword! What happens?";
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
