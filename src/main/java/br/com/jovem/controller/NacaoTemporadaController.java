package br.com.jovem.controller;

import br.com.jovem.dto.NacaoTemporadaDTO;
import br.com.jovem.request.NacaoTemporadaRequest;
import br.com.jovem.service.NacaoTemporadaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/nacoes-temporada")
public class NacaoTemporadaController {

    @Autowired
    private NacaoTemporadaService service;

    @PostMapping
    public NacaoTemporadaDTO criar(
            @RequestBody @Valid NacaoTemporadaRequest request
    ) {

        return service.criar(request);
    }

    @PutMapping("/{id}")
    public NacaoTemporadaDTO editar(
            @PathVariable UUID id,
            @RequestBody @Valid NacaoTemporadaRequest request
    ) {

        return service.editar(id, request);
    }

    @GetMapping("/{id}")
    public NacaoTemporadaDTO buscarPorId(@PathVariable UUID id) {

        return service.buscarPorId(id);
    }

    @GetMapping
    public List<NacaoTemporadaDTO> listar() {

        return service.listar();
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {

        service.excluir(id);
    }

}