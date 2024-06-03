package com.yangyag.toy.web;

import com.yangyag.toy.service.GenerativeAI;
import com.yangyag.toy.web.dto.gpt.ChatGPTRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GPTController {

    private final GenerativeAI generativeAI;

    @PostMapping("/gpt/question")
    public ResponseEntity<String> question(@RequestBody ChatGPTRequest chatGPTRequest) {
        String result = generativeAI.question(chatGPTRequest.getPrompt(), chatGPTRequest.getModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
