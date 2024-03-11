package com.yangyag.toy.service.impl;

import com.yangyag.toy.service.StrategyService;
import org.springframework.stereotype.Service;

@Service("strategy1")
public class ConcreteStrategy1ServiceImplService implements StrategyService {

    @Override
    public void executeStrategy(String strategyName) {
        if ("strategy1".equals(strategyName)) {
            System.out.println("ConcreteStrategy1 실행");
        }
    }
}
