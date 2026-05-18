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

    // @Pattern(regexp = "(?i)Pedido|devolucion", message = "[+] El Tipo debe ser
    // Pendiente, Enviado, Cancelado o Reservado...")
    // en veremos todavia
    @NotBlank(message = "[+] El tipo no puede estar vacio...")
    private String tipo;

    @Pattern(regexp = "(?i)Pendiente|Preparacion|Enviado|Cancelado", message = "[+] El Tipo debe ser Pendiente, Preparacion, Enviado o Cancelado")
    @NotBlank(message = "[+] El estado no puede estar vacio...")
    private String estado;

}
