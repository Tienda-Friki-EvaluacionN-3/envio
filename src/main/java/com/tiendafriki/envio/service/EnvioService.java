package com.tiendafriki.envio.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tiendafriki.envio.dto.EstadoEnvioDTO;
import com.tiendafriki.envio.model.Envio;
import com.tiendafriki.envio.model.Pedido;
import com.tiendafriki.envio.repository.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository repository;

    // === (GET) LISTAR === //

    public List<Envio> listar() {

        return repository.findAll();
    }

    // === (GET) BUSCAR POR ID === //

    public Envio buscarPorId(Integer id) {

        return repository.findById(id)

                .orElseThrow(() ->

                        new NoSuchElementException(
                                "[ERROR] Envio no encontrado [X_X] ..."
                        )
                );
    }

    // === (GET) BUSCAR POR PEDIDO === //

    public Envio buscarPorPedido(Integer pedidoId) {

        return repository.findByPedidoId(pedidoId)

                .orElseThrow(() ->

                        new NoSuchElementException(
                                "[ERROR] No existe un envio asociado a ese pedido [X_X] ..."
                        )
                );
    }

    // === FUNCION PARA VALIDAR PEDIDO === //

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

    // === (POST) GUARDAR === //

    public String guardar(Envio envio) {

        // Validar pedido existente

        Pedido pedido =
                validarPedido(envio.getPedidoId());

        // Validar estado PAGADO

        if (!pedido.getEstado().equalsIgnoreCase("Pagado")) {

            throw new IllegalArgumentException(
                    "[ERROR] El pedido debe estar PAGADO para crear un envio [X_X] ..."
            );
        }

        // Validar que no exista un envio activo

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

        // Estado automático inicial

        envio.setEstado("Pendiente");

        repository.save(envio);

        return "[+] El envio fue agregado correctamente";
    }


    // === (PUT) ACTUALIZAR ESTADO === //

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

        // Obtener nuevo estado desde DTO

        String nuevoEstado =
                dto.getEstado();

        // === CONTROL DE FLUJO === //

        if (estadoActual.equalsIgnoreCase("Cancelado")) {

            throw new IllegalArgumentException(
                    "[ERROR] No se puede modificar un envio cancelado [X_X] ..."
            );
        }

        // Pendiente -> Preparacion

        if (estadoActual.equalsIgnoreCase("Pendiente")
                && nuevoEstado.equalsIgnoreCase("Preparacion")) {

            envio.setEstado("Preparacion");
        }

        // Preparacion -> Enviado

        else if (estadoActual.equalsIgnoreCase("Preparacion")
                && nuevoEstado.equalsIgnoreCase("Enviado")) {

            envio.setEstado("Enviado");
        }

        // Pendiente -> Cancelado

        else if (estadoActual.equalsIgnoreCase("Pendiente")
                && nuevoEstado.equalsIgnoreCase("Cancelado")) {

            envio.setEstado("Cancelado");
        }

        // Preparacion -> Cancelado

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

    // === (DELETE) ELIMINAR === //

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


/*

// ===  (GET) LISTAR === //

    public List<Envio> listar() {
        return repository.findAll();
    }

    // ===  (GET) BUSCAR POR ID === //

    public Optional<Envio> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    // ===  (POST) GUARDAR === //

    public String guardar(Envio envio) {
        repository.save(envio);
        return "[+] El Envio fue agregado correctamente";
    }

    // ===  (PUT) ACTUALIZAR  === //

    public String actualizar(Envio envio) {
        
        List<Envio> lista = repository.findAll();
        for (Envio p : lista) {
            if (p.getId().equals(envio.getId())) {
                repository.save(envio);
                return "[+] El Envio fue actualizado correctamente";
            }
        }
        return "[+] El Envio no fue encontrado";

    }

    public String eliminar(Integer id) {
        List<Envio> lista = repository.findAll();
        for (Envio p : lista) {
            if (p.getId().equals(id)) {
                repository.deleteById(id);
                return "[+] El Envio fue eliminado correctamente";
            }

        }
        return "[+] El Envio no fue encontrado";
    }


*/
