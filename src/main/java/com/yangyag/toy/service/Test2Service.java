package com.yangyag.toy.service;

import com.yangyag.toy.service.impl.Hello;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Test2Service implements Hello {
    @Override
    public String sayHello() {
        return "Test2 Hello";
    }

}
