package br.com.jovem.controller;

import br.com.jovem.dto.TemporadaDTO;
import br.com.jovem.request.TemporadaRequest;
import br.com.jovem.service.TemporadaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/temporadas")
public class TemporadaController {

    @Autowired
    private TemporadaService service;

    @PostMapping
    public TemporadaDTO criar(@RequestBody @Valid TemporadaRequest request) {

        return service.criar(request);
    }

    @PutMapping("/{id}")
    public TemporadaDTO editar(@PathVariable UUID id,
                               @RequestBody @Valid TemporadaRequest request) {

        return service.editar(id, request);
    }

    @GetMapping("/{id}")
    public TemporadaDTO buscarPorId(@PathVariable UUID id) {

        return service.buscarPorId(id);
    }

    @GetMapping
    public List<TemporadaDTO> listar() {

        return service.listar();
    }

    @PatchMapping("/{id}/iniciar")
    public void iniciar(@PathVariable UUID id) {

        service.iniciar(id);
    }

    @PatchMapping("/{id}/encerrar")
    public void encerrar(@PathVariable UUID id) {

        service.encerrar(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {

        service.excluir(id);
    }

}