package com.tiendafriki.envio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendafriki.envio.model.Envio;
import com.tiendafriki.envio.service.EnvioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/envio")
public class EnvioController {

    private EnvioService service;

    public EnvioController(EnvioService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public List<Envio> listar() {
        return service.listar();

    }

    @GetMapping("/buscarId/{id}")
    public Optional<Envio> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> crearPedido(@Valid @RequestBody Envio envio) {
        String mensaje = service.guardar(envio);
        return ResponseEntity.status(201).body(mensaje);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@Valid @RequestBody Envio envio) {
        String mensaje = service.actualizar(envio);
        return ResponseEntity.status(200).body(mensaje);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String mensaje = service.eliminar(id);
        return ResponseEntity.status(200).body(mensaje);

    }

}
