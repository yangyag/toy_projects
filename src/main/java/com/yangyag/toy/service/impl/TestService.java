package com.yangyag.toy.service.impl;

import com.yangyag.toy.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class TestService implements HelloService {
    @Override
    public String sayHello() {
        return "Test Hello";
    }
}
