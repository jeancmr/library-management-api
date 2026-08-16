package com.jeancmr.library_management.security.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private static final String SCHEMA_NAME = "BearerAuth";
    private static final String BEARER_FORMAT = "JWT";
    private static final String DESCRIPTION = "JWT Auth for Library Management API";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SCHEMA_NAME))
                .components(new Components()
                        .addSecuritySchemes(SCHEMA_NAME,
                                new SecurityScheme()
                                        .name(SCHEMA_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat(BEARER_FORMAT)
                                        .in(SecurityScheme.In.HEADER)
                                        .description(DESCRIPTION)
                        )
                )
                .info(new Info()
                        .title("Library Management API")
                        .version("1.0")
                        .description("RESTful API for library management")
                        .contact(new Contact()
                                .name("Jean Madiedo Rodríguez")
                                .url("https://github.com/jeancmr"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.txt"))
                );
    }
}
