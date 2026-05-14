package com.tiendafriki.envio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "(?i)Creado|Pagado|Cancelado", message = "[+] El Tipo debe ser Pendiente, Enviado, Cancelado o Reservado...")
    @NotBlank(message = "[+] El estado no puede estar vacio...")
    private String estado;

}
