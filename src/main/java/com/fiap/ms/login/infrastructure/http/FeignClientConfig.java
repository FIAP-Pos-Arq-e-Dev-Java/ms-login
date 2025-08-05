package com.fiap.ms.login.infrastructure.http;

import com.fiap.ms.login.infrastructure.config.security.JwtTokenHolder;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor jwtPropagationInterceptor() {
        return template -> {
            String token = JwtTokenHolder.getToken();
            if (token != null) {
                template.header("Authorization", "Bearer " + token);
            }
        };
    }
}