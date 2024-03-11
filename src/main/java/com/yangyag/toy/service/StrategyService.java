package com.yangyag.toy.service;


import org.springframework.stereotype.Service;

@Service
public interface StrategyService {
    void executeStrategy(String strategyName);
}
