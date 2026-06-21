package com.tiendafriki.envio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer pedidoId;

    @Pattern(regexp = "(?i)Pendiente|Preparacion|Enviado|Cancelado", message = "[+] El Tipo debe ser Pendiente, Preparacion, Enviado o Cancelado")
    private String estado;

}
