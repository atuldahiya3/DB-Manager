package com.dbmanager.dbmanager.Service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.model}")
    private String model;

    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String convertToSQL(String userPrompt) {
        String fullPrompt = """
                Convert the following natural language instruction to a MySQL query.
                Assume a table 'users' with columns: id, name, email, created_at.

                Instruction: %s
                SQL:
                """.formatted(userPrompt);

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("prompt", fullPrompt);
        requestBody.put("temperature", 0);
        requestBody.put("max_tokens", 150);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_URL, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject json = new JSONObject(response.getBody());
            String sql = json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getString("text")
                    .trim();

            // Optional: ensure it ends with semicolon
            return sql.endsWith(";") ? sql : sql + ";";
        } else {
            throw new RuntimeException("OpenAI API error: " + response.getBody());
        }
    }
}