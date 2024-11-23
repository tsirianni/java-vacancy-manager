package org.personal.Vacancy_Manager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Vacancy Manager").description("API to handle vacancies management").version("1.0.0"))
                            .schemaRequirement("jwt_auth", createSecurityScheme());
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme().name("jwt_auth")
                                   .scheme("bearer")
                                   .bearerFormat("JWT")
                                   .type(SecurityScheme.Type.HTTP)
                                   .in(SecurityScheme.In.HEADER);
    }
}
