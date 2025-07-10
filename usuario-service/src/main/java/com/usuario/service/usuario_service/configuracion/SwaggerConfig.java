package com.usuario.service.usuario_service.configuracion;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Usuario Service API")
                        .description("Documentación del microservicio de usuarios")
                        .version("1.0"));
    }
}