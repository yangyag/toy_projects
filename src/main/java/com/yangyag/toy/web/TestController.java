package com.yangyag.toy.web;

import com.yangyag.toy.service.GenerativeAI;
import com.yangyag.toy.service.NewsPublisher;
import com.yangyag.toy.web.dto.gpt.ChatGPTRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final NewsPublisher newsPublisher;

    @GetMapping("/news/publish")
    public ResponseEntity<Object> publishing(@RequestParam String comment) {
        newsPublisher.publishNews(comment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
