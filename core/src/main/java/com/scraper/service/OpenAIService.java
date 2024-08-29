package com.scraper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;

    /**
     * Sends a prompt to the OpenAI GPT model and retrieves the generated response as a string.
     * This method constructs an HTTP POST request with the given prompt and required headers,
     * then sends the request to the OpenAI API endpoint specified by the URL.
     *
     * @param prompt the input prompt to be processed by the GPT model. It should be a string
     *               that includes the context or instructions for the desired AI-generated response.
     * @return a String containing the AI-generated response from the GPT model based on the provided prompt.
     */
    public String getChatGptResponse(String prompt) {
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{Map.of("role", "user", "content", prompt)});

        // Create an HttpEntity with headers and body
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Safely create the URI
        URI uri = URI.create(OPENAI_URL);

        // Make the API call
        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Return the response body as a String
        return response.getBody();
    }
}
