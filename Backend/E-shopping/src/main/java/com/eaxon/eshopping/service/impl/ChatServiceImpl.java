package com.eaxon.eshopping.service.impl;

import com.eaxon.eshopping.model.dto.ChatRequestDTO;
import com.eaxon.eshopping.model.dto.ChatResponseDTO;
import com.eaxon.eshopping.service.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ChatResponseDTO sendMessage(ChatRequestDTO request) {
        String chatEndpoint = baseUrl + "/chat/completions";
        
        // Set headers with API key
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        
        // Create request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", request.getMessage());
        messages.add(userMessage);
        
        requestBody.put("messages", messages);
        
        // Send request to DeepSeek API
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        Map<String, Object> response = restTemplate.postForObject(chatEndpoint, entity, Map.class);
        
        // Extract response content
        String responseContent = "";
        if (response != null && response.containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                responseContent = (String) message.get("content");
            }
        }
        
        // Create and return the response DTO
        ChatResponseDTO responseDTO = new ChatResponseDTO();
        responseDTO.setResponse(responseContent);
        responseDTO.setUserId(request.getUserId());
        responseDTO.setSessionId(UUID.randomUUID().toString());
        
        return responseDTO;
    }
} 