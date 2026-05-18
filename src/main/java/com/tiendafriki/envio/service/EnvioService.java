package com.tiendafriki.envio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendafriki.envio.model.Envio;
import com.tiendafriki.envio.repository.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository repository;

    public List<Envio> listar() {
        return repository.findAll();
    }

    public Optional<Envio> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public String guardar(Envio envio) {
        repository.save(envio);
        return "[+] El Envio fue agregado correctamente";
    }

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
}
