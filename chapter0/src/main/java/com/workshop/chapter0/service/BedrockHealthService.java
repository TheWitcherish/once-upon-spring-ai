package com.workshop.chapter0.service;

import com.workshop.chapter0.model.BedrockHealthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BedrockHealthService {
    
    private static final Logger log = LoggerFactory.getLogger(BedrockHealthService.class);
    private final ChatClient chatClient;
    
    @Value("${spring.ai.bedrock.converse.chat.options.model}")
    private String modelId;

    public BedrockHealthService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public BedrockHealthResponse testInvoke() {
        try {
            log.info("Testing Bedrock connectivity...");
            String response = chatClient.prompt()
                .user("Say 'Hello from Claude!' in one sentence.")
                .call()
                .content();
            
            log.info("Bedrock test successful");
            return new BedrockHealthResponse(true, modelId, response);
        } catch (Exception e) {
            log.error("Bedrock test failed", e);
            return new BedrockHealthResponse(false, modelId, "Error: " + e.getMessage());
        }
    }
}
