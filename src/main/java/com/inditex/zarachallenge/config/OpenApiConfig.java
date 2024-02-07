package com.inditex.zarachallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("ZCH")
                        .description("Zara challenge")
                        .version("v0.1.0")
                        .contact(new Contact()
                                .name("Javier")
                                .email("javierfn@gmail.com"))
                );
    }

}
