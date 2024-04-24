package com.yangyag.toy.service;

import com.yangyag.toy.event.NewsEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewsListener implements ApplicationListener<NewsEvent> {

    @Override
    public void onApplicationEvent(NewsEvent event) {
        System.out.println("Received news: " + event.getNews());
    }
}
