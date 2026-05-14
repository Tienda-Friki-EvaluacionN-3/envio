package com.tiendafriki.envio;

import com.tiendafriki.envio.model.Envio;
import com.tiendafriki.envio.repository.EnvioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(EnvioRepository envioRepository) {
        return args -> {
            if (envioRepository.count() == 0) {

                envioRepository.save(new Envio(null,
                        "Pedido",
                        "Pendiente"));
                envioRepository.save(new Envio(null,
                        "Pedido",
                        "Preparacion"));
                envioRepository.save(new Envio(null,
                        "Pedido",
                        "Enviado"));
                envioRepository.save(new Envio(null,
                        "Pedido",
                        "Cancelado"));
                envioRepository.save(new Envio(null,
                        "Devolucion",
                        "Pendiente"));
                envioRepository.save(new Envio(null,
                        "Devolucion",
                        "Preparacion"));
                envioRepository.save(new Envio(null,
                        "Devolucion",
                        "Enviado"));
                envioRepository.save(new Envio(null,
                        "Devolucion",
                        "Cancelado"));
                envioRepository.save(new Envio(null,
                        "Pedido",
                        "Pendiente"));
                envioRepository.save(new Envio(null,
                        "Pedido",
                        "Enviado"));
            }
        };
    }

}
