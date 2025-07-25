package com.katsadourose.primary_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .description("API for managing users in the primary service")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Evangelos Katsadouros")
                                .email("katsadouros.v@gmail.com")));
    }
}