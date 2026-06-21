package com.tiendafriki.envio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tiendafriki.envio.dto.EstadoEnvioDTO;
import com.tiendafriki.envio.model.Envio;
import com.tiendafriki.envio.service.EnvioService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnvioService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Envio crearEnvio() {
        return new Envio(
                1,
                1,
                "Pendiente");
    }

    private EstadoEnvioDTO crearEstadoEnvioDTO() {

        EstadoEnvioDTO dto = new EstadoEnvioDTO();
        dto.setEstado("Preparacion");

        return dto;
    }

    @Test
    void listarEnvios() throws Exception {

        when(service.listar())
                .thenReturn(List.of(crearEnvio()));

        mockMvc.perform(get("/envio/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarEnvioPorId() throws Exception {

        when(service.buscarPorId(1))
                .thenReturn(crearEnvio());

        mockMvc.perform(get("/envio/buscarId/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarEnvioPorPedido() throws Exception {

        when(service.buscarPorPedido(1))
                .thenReturn(crearEnvio());

        mockMvc.perform(get("/envio/buscarPedido/1"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarEnvio() throws Exception {

        when(service.guardar(any()))
                .thenReturn("[+] El envio fue agregado correctamente");

        mockMvc.perform(post("/envio/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearEnvio())))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarEstadoEnvio() throws Exception {

        when(service.actualizarEstado(any(), any()))
                .thenReturn("[+] Estado del envio actualizado correctamente");

        mockMvc.perform(put("/envio/actualizarEstado/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearEstadoEnvioDTO())))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarEnvio() throws Exception {

        when(service.eliminar(1))
                .thenReturn("[+] El envio fue eliminado correctamente");

        mockMvc.perform(delete("/envio/eliminar/1"))
                .andExpect(status().isOk());
    }

}
