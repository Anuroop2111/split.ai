package com.split.ai.split.service.server.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.split.ai.*")
@OpenAPIDefinition
public class SplitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SplitApplication.class, args);
    }
}
