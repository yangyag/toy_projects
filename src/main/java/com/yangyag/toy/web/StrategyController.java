package com.yangyag.toy.web;

import com.yangyag.toy.service.StrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StrategyController {

    private final Map<String, StrategyService> strategyMap;

    @GetMapping("/strategy/{strategyName}")
    public ResponseEntity<String> executeStrategy(@PathVariable String strategyName) {
        StrategyService strategyService = strategyMap.get(strategyName);
        if (strategyService != null) {
            strategyService.executeStrategy(strategyName);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
