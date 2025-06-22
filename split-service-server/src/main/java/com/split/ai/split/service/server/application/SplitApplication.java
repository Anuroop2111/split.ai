package com.split.ai.split.service.server.application;

@ComponentScan("com.split.ai.*")
@OpenAPIDefinition
public class SplitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SplitApplication.class, args);
    }
}
