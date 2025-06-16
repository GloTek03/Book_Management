package com.ltfullstack.generate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ltfullstack.generate.model.GenerativeRequest;
import com.ltfullstack.generate.service.GenerativeService;

@RestController
@RequestMapping("/api/v1/generative")
public class GenerativeController {
    @Autowired
    private GenerativeService generativeService;

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody String prompt) {
        // Here you would typically call a service to handle the generation logic
        // For now, we will just return the prompt as a placeholder
        GenerativeRequest request = new GenerativeRequest();
        request.setPrompt(prompt);
        String result = generativeService.generateContent(prompt);
        return ResponseEntity.ok(result);
    }
}
