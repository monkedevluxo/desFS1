package com.envio.service.envio_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(title = "Envio Service API", version = "1.0", description = "Documentación Envíos")
)

@SpringBootApplication
public class EnvioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnvioServiceApplication.class, args);
	}

}
