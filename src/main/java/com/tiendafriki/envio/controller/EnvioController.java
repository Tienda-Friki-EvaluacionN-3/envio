package com.tiendafriki.envio.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendafriki.envio.dto.EstadoEnvioDTO;
import com.tiendafriki.envio.model.Envio;
import com.tiendafriki.envio.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/envio")
public class EnvioController {

    private EnvioService service;

    public EnvioController(EnvioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar envios", description = "Obtiene una lista con todos los envios registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")
    public List<Envio> listar() {
        return service.listar();

    }

    @Operation(summary = "Buscar envío por ID", description = "Busca un envío vinculado a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/buscarId/{id}")
    public Envio buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Buscar envío por pedido", description = "Busca el envío vinculado al ID del pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un envío asociado al pedido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/buscarPedido/{pedidoId}")
    public Envio buscarPorPedido(@PathVariable Integer pedidoId) {

        return service.buscarPorPedido(pedidoId);
    }

    @Operation(summary = "Agregar envío", description = "Agrega un nuevo envío en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Envío registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/agregar")
    public ResponseEntity<String> crearPedido(@Valid @RequestBody Envio envio) {
        String mensaje = service.guardar(envio);
        return ResponseEntity.status(201).body(mensaje);
    }

    @Operation(summary = "Actualizar estado del envío", description = "Permite modificar el estado de un envío registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizarEstado/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Valid @RequestBody EstadoEnvioDTO dto) {
        String mensaje = service.actualizarEstado(id, dto);
        return ResponseEntity.status(200).body(mensaje);
    }

    @Operation(summary = "Eliminar envío", description = "Elimina un envío registrado vinculado a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String mensaje = service.eliminar(id);
        return ResponseEntity.status(200).body(mensaje);

    }

}
