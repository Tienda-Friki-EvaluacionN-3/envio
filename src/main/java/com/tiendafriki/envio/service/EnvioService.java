package com.tiendafriki.envio.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tiendafriki.envio.dto.EnvioRequestDTO;
import com.tiendafriki.envio.dto.EstadoEnvioDTO;
import com.tiendafriki.envio.model.Envio;
import com.tiendafriki.envio.model.Pedido;
import com.tiendafriki.envio.repository.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository repository;

    public List<Envio> listar() {

        return repository.findAll();
    }

    public Envio buscarPorId(Integer id) {

        return repository.findById(id)

                .orElseThrow(() ->

                        new NoSuchElementException(
                                "[ERROR] Envio no encontrado [X_X] ..."
                        )
                );
    }

    public Envio buscarPorPedido(Integer pedidoId) {

        return repository.findByPedidoId(pedidoId)

                .orElseThrow(() ->

                        new NoSuchElementException(
                                "[ERROR] No existe un envio asociado a ese pedido [X_X] ..."
                        )
                );
    }

    private Pedido validarPedido(Integer pedidoId) {

        RestTemplate rt = new RestTemplate();

        String url =
                "http://localhost:8085/pedidos/buscarId/"
                        + pedidoId;

        try {

            Pedido pedido =
                    rt.getForObject(url, Pedido.class);

            if (pedido == null) {

                throw new NoSuchElementException(
                        "[ERROR] Pedido no encontrado [X_X] ..."
                );
            }

            return pedido;

        } catch (Exception e) {

            throw new RuntimeException(
                    "[ERROR] No se pudo validar el pedido [X_X] ..."
            );
        }
    }

    public String guardar(EnvioRequestDTO envio) {

        Pedido pedido =
                validarPedido(envio.getPedidoId());

        if (!pedido.getEstado().equalsIgnoreCase("Pagado")) {

            throw new IllegalArgumentException(
                    "[ERROR] El pedido debe estar PAGADO para crear un envio [X_X] ..."
            );
        }

        Optional<Envio> envioExistente =
                repository.findByPedidoIdAndEstadoIn(
                        envio.getPedidoId(),
                        List.of(
                                "Pendiente",
                                "Preparacion",
                                "Enviado"
                        )
                );

        if (envioExistente.isPresent()) {

            throw new IllegalArgumentException(
                    "[ERROR] Ya existe un envio activo para este pedido [X_X] ..."
            );
        }

        Envio env = new Envio();

        env.setPedidoId(envio.getPedidoId());

        env.setEstado("Pendiente");

        repository.save(env);

        return "[+] El envio fue agregado correctamente";
    }

    public String actualizarEstado(Integer id,EstadoEnvioDTO dto) {

        Optional<Envio> envioOpt =
                repository.findById(id);

        if (envioOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Envio no encontrado [X_X] ..."
            );
        }

        Envio envio = envioOpt.get();

        String estadoActual =
                envio.getEstado();

        String nuevoEstado =
                dto.getEstado();

        if (estadoActual.equalsIgnoreCase("Cancelado")) {

            throw new IllegalArgumentException(
                    "[ERROR] No se puede modificar un envio cancelado [X_X] ..."
            );
        }

        if (estadoActual.equalsIgnoreCase("Pendiente")
                && nuevoEstado.equalsIgnoreCase("Preparacion")) {

            envio.setEstado("Preparacion");
        }

        else if (estadoActual.equalsIgnoreCase("Preparacion")
                && nuevoEstado.equalsIgnoreCase("Enviado")) {

            envio.setEstado("Enviado");
        }

        else if (estadoActual.equalsIgnoreCase("Pendiente")
                && nuevoEstado.equalsIgnoreCase("Cancelado")) {

            envio.setEstado("Cancelado");
        }

        else if (estadoActual.equalsIgnoreCase("Preparacion")
                && nuevoEstado.equalsIgnoreCase("Cancelado")) {

            envio.setEstado("Cancelado");
        }

        else {

            throw new IllegalArgumentException(
                    "[ERROR] Flujo de estado invalido [X_X] ..."
            );
        }

        repository.save(envio);

        return "[+] Estado del envio actualizado correctamente";
    }

    public String eliminar(Integer id) {

        Optional<Envio> envioOpt =
                repository.findById(id);

        if (envioOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Envio no encontrado [X_X] ..."
            );
        }

        repository.deleteById(id);

        return "[+] El envio fue eliminado correctamente";
    }

    
}