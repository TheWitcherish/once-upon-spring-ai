import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
import org.springframework.ai.bedrock.converse.BedrockChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

import java.io.IOException;
import java.util.Map;

private static final Logger log = LoggerFactory.getLogger("DungeonMasterWithBuiltInTools");

void main() throws IOException {
    log.info("=== Starting AI Agent with Built-in Tools ===");

    // Step 1: Use Jsoup (bundled with spring-ai-jsoup-document-reader) to fetch web content
    var wikipediaUrl = "https://en.wikipedia.org/wiki/Dungeons_%26_Dragons";
    log.info("Fetching content from: {}", wikipediaUrl);

    // Jsoup.connect() allows setting User-Agent and other HTTP headers
    var jsoupDoc = Jsoup.connect(wikipediaUrl)
        .userAgent("SpringAI-Agent/1.0 (Educational Demo)")
        .timeout(30000)
        .get();

    // Extract text content from the page
    var webContent = jsoupDoc.body().text();
    log.info("Fetched {} characters from Wikipedia", webContent.length());

    // Wrap in Spring AI's Document abstraction
    var document = new Document(webContent, Map.of(
        "source", wikipediaUrl,
        "title", jsoupDoc.title()
    ));

    // Step 2: Create AWS Bedrock Runtime Client
    var bedrockClient = BedrockRuntimeClient.builder()
        .region(Region.EU_CENTRAL_1)
        .credentialsProvider(DefaultCredentialsProvider.builder().build())
        .build();

    // Step 3: Configure model options
    var modelId = "us.anthropic.claude-haiku-4-5-20251001-v1:0";
    var options = BedrockChatOptions.builder()
        .model(modelId)
        .build();

    // Step 4: Create Spring AI ChatModel
    var chatModel = BedrockProxyChatModel.builder()
        .bedrockRuntimeClient(bedrockClient)
        .defaultOptions(options)
        .build();

    // Step 5: Build ChatClient
    var agent = ChatClient.builder(chatModel).build();

    // Step 6: Ask the AI to extract information from the fetched content
    var contentToAnalyze = document.getText()
        .substring(0, Math.min(document.getText().length(), 15000));

    var userMessage = """
        Based on the following Wikipedia content about Dungeons & Dragons,
        tell me the name of the designers of Dungeons and Dragons.

        Wikipedia Content:
        %s
        """.formatted(contentToAnalyze);

    log.info("Asking AI to extract D&D designers from Wikipedia content...\n");

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
