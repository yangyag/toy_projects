package com.yangyag.toy.service.impl;

import com.yangyag.toy.service.StrategyService;
import org.springframework.stereotype.Service;

@Service
public class ConcreteStrategy2ServiceImplService implements StrategyService {

    @Override
    public void executeStrategy(String strategyName) {
        if ("strategy2".equals(strategyName)) {
            System.out.println("ConcreteStrategy2 실행");
        }
    }
}
