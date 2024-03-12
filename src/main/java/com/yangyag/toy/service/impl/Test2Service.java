package com.yangyag.toy.service.impl;

import com.yangyag.toy.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class Test2Service implements HelloService {
    @Override
    public String sayHello() {
        return "Test2 Hello";
    }

}
