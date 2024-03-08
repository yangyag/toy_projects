package com.yangyag.toy.config;

// WebConfig.java
import com.yangyag.toy.security.JwtAuthenticationFilter;
import com.yangyag.toy.util.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtils jwtUtils) {
        return new JwtAuthenticationFilter(jwtUtils);
    }
}