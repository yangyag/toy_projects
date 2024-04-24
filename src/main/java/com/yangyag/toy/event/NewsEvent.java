package com.yangyag.toy.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewsEvent extends ApplicationEvent {
    private final String news;


    public NewsEvent(Object source, String news) {
        super(source);
        this.news = news;
    }
}
