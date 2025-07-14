package com.MSVC_EduTech.Alumno.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("APIRESTFULL - MSVC - alumnos")
                        .description("Esta es la sección donde todos los endpoints de MSVC alumnos")
                        .version("1.0.0")

                );
    }
}
