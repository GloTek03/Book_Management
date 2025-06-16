package com.ltfullstack.generate.service;

import org.springframework.stereotype.Service;

import com.ltfullstack.generate.component.GeminiClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenerativeService {
    // This service will handle the logic for generating content based on prompts
    // You can implement methods to interact with a generative model or API here

    private final GeminiClient geminiClient;

    public String generateContent(String prompt) {
        // Placeholder for actual generation logic
        return geminiClient.callGemini(prompt);
    }
}
