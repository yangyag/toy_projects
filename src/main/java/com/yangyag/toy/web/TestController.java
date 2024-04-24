package com.yangyag.toy.web;

import com.yangyag.toy.service.NewsPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
