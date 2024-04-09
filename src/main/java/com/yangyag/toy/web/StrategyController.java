package com.yangyag.toy.web;

import com.yangyag.toy.service.StrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StrategyController {

    private final Map<String, StrategyService> strategyMap;

    @GetMapping("/strategy/{strategyName}")
    public ResponseEntity<String> executeStrategy(@PathVariable String strategyName) {
        Optional<StrategyService> strategyService = Optional.ofNullable(strategyMap.get(strategyName));

        strategyService.ifPresent(service -> service.executeStrategy(strategyName));

        return strategyService
                .map(service -> ResponseEntity.ok("Strategy executed successfully"))
                .orElseGet(() -> ResponseEntity.badRequest().body("Invalid strategy: " + strategyName));
    }
}
