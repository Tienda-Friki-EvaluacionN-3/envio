package com.tiendafriki.envio.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Para creaer el Envio, solo se ingresa el id de pedido al que pertenece

@Data
public class EnvioRequestDTO {

    @NotNull(message = "[ERROR] El id de pedido no puede estar vacio [X_X]...")
    private Integer pedidoId;

}
