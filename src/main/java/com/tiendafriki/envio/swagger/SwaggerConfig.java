package com.tiendafriki.envio.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI pedidosOpenAPI() {
        return new OpenAPI()
                .info(new Info()

                        .title("API de Envios")

                        .description("API REST para la gestion de envios")

                        .version("1.0")

                        .contact(new Contact()
                                .name("TiendaFriki")
                                .email("soporte@tiendafriki.com")
                            )
                            );
    }

}
