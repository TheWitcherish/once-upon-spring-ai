package com.workshop.chapter0.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.ai.bedrock")
public record BedrockConfigProperties(
    Aws aws,
    Anthropic anthropic
) {
    public record Aws(String region, String accessKey, String secretKey) {}
    public record Anthropic(Claude claude) {}
    public record Claude(String model, Options options) {}
    public record Options(double temperature, int maxTokens) {}
}
