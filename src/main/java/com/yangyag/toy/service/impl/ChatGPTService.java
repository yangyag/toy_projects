package com.yangyag.toy.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangyag.toy.service.GenerativeAI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ChatGPTService implements GenerativeAI {

    @Value("${gpt.api-key}")
    private String apiKey;

    @Override
    public String question(String prompt, String model) {
        System.out.println("Method question started");
        String urlString = "https://api.openai.com/v1/chat/completions";
        String content = "";

        try {
            System.out.println("Connecting to URL: " + urlString);
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);  // 10 seconds
            conn.setReadTimeout(30000);  // 30 seconds
            System.out.println("Connection setup complete");

            String data = "{"
                    + "\"model\": \"" + model + "\","
                    + "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]"
                    + "}";
            System.out.println("Sending data: " + data);
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                StringBuilder response = new StringBuilder();
                while ((output = br.readLine()) != null) {
                    response.append(output);
                }
                br.close();

                content = response.toString();
                System.out.println("Response: " + content);

                // JSON 응답을 파싱하여 content 추출
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(content);
                content = rootNode.path("choices").get(0).path("message").path("content").asText();
                System.out.println("Extracted content: " + content);
            } else {
                System.out.println("HTTP error code: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }
}
