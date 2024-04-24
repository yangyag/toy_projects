package com.yangyag.toy.service;

import com.yangyag.toy.event.NewsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishNews(String news) {
        NewsEvent newsEvent = new NewsEvent(this, news);
        publisher.publishEvent(newsEvent);
    }
}
