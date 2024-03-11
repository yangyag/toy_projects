package com.yangyag.toy;


import com.yangyag.toy.service.StrategyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Map<String, StrategyService> strategyMap(List<StrategyService> strategyServices) {
        Map<String, StrategyService> map = new HashMap<>();
        for (StrategyService strategyService : strategyServices) {
            map.put(strategyService.getClass().getSimpleName().toLowerCase(), strategyService);
        }
        return map;
    }
}
