package com.tiendafriki.envio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EstadoEnvioDTO {

    // Este DTO corresponde a la actualización de los estados

    // Solo se puede actualizar el estado del envio

    @Pattern(regexp = "(?i)Pendiente|Preparacion|Enviado|Cancelado", message = "[+] El Tipo debe ser Pendiente, Preparacion, Enviado o Cancelado")
    @NotBlank(message = "[+] El estado no puede estar vacio...")
    private String estado;

}
