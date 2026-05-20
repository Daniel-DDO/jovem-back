package br.com.jovem.controller;

import br.com.jovem.dto.TriboTemporadaDTO;
import br.com.jovem.request.TriboTemporadaRequest;
import br.com.jovem.service.TriboTemporadaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tribos-temporada")
public class TriboTemporadaController {

    @Autowired
    private TriboTemporadaService service;

    @PostMapping
    public TriboTemporadaDTO criar(
            @RequestBody @Valid TriboTemporadaRequest request
    ) {

        return service.criar(request);
    }

    @PutMapping("/{id}")
    public TriboTemporadaDTO editar(
            @PathVariable UUID id,
            @RequestBody @Valid TriboTemporadaRequest request
    ) {

        return service.editar(id, request);
    }

    @GetMapping("/{id}")
    public TriboTemporadaDTO buscarPorId(@PathVariable UUID id) {

        return service.buscarPorId(id);
    }

    @GetMapping
    public List<TriboTemporadaDTO> listar() {

        return service.listar();
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {

        service.excluir(id);
    }

}