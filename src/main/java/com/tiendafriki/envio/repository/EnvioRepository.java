package com.tiendafriki.envio.repository;


import com.tiendafriki.envio.model.Envio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {

    // Esta función de repository nos permitirá buscar un envio por id e de pedido y por el estado

    Optional<Envio> findByPedidoIdAndEstadoIn(Integer pedidoId, List<String> estados);

    // Permitw buscar Envios por Id de Pedido al que pertenecen
    Optional<Envio> findByPedidoId(Integer pedidoId);

}
