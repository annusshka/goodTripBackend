package com.example.goodTripBackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ann Telegina",
                        email = "telegina.ann2003@yandex.ru"
                ),
                description = "OpenApiDocumentation for Mobile App \"UnknownPlace\"",
                title = "UnknownPlace",
                version = "1.0.0",
                license = @License()
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
