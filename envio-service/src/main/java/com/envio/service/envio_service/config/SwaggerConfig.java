package com.envio.service.envio_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
            .info(new Info().title("API Envío")
            .description("Documentación API del microservicio Envío")
            .version("1.0"));
    }
}
