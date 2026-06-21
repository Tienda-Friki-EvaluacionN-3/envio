package com.tiendafriki.envio.repository;


import com.tiendafriki.envio.model.Envio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {

    Optional<Envio> findByPedidoIdAndEstadoIn(Integer pedidoId, List<String> estados);

    Optional<Envio> findByPedidoId(Integer pedidoId);

}
