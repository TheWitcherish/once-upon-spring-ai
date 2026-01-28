package com.workshop.chapter0.controller;

import com.workshop.chapter0.model.BedrockHealthResponse;
import com.workshop.chapter0.model.HealthResponse;
import com.workshop.chapter0.service.BedrockHealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Chapter0Controller {

    private final BedrockHealthService bedrockHealthService;

    public Chapter0Controller(BedrockHealthService bedrockHealthService) {
        this.bedrockHealthService = bedrockHealthService;
    }

    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok(new HealthResponse("UP", "Chapter 0 is running"));
    }

    @GetMapping("/bedrock/test")
    public ResponseEntity<BedrockHealthResponse> testBedrock() {
        BedrockHealthResponse response = bedrockHealthService.testInvoke();
        return ResponseEntity.ok(response);
    }
}
