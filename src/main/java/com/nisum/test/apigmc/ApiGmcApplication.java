package com.nisum.test.apigmc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot REST GMC-API Documentation",
                description = "Prueba tecnica NISUM",
                version = "v1.0",
                contact = @Contact(
                        name = "German Marcel Macias Cano",
                        email = "germacias@gmail.com"
                )
        )
)
public class ApiGmcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGmcApplication.class, args);
    }

}
