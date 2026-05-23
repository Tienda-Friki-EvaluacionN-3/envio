package com.tiendafriki.envio.model;

import lombok.Data;

// Esta entidad auxiliar nos permitirá
// conservar los datos que necesitamos del pedido

@Data
public class Pedido {

    private Integer id;
    private String estado;

}
