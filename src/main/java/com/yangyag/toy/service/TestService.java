package com.yangyag.toy.service;

import com.yangyag.toy.service.impl.Hello;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TestService implements Hello {
    @Override
    public String sayHello() {
        return "Test Hello";
    }
}
