package br.com.jovem.controller;

import br.com.jovem.dto.EventoPontuacaoDTO;
import br.com.jovem.request.EventoPontuacaoRequest;
import br.com.jovem.service.EventoPontuacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eventos-pontuacao")
public class EventoPontuacaoController {

    @Autowired
    private EventoPontuacaoService service;

    @PostMapping
    public EventoPontuacaoDTO criar(
            @RequestBody @Valid EventoPontuacaoRequest request
    ) {

        return service.criar(request);
    }

    @GetMapping("/{id}")
    public EventoPontuacaoDTO buscarPorId(@PathVariable UUID id) {

        return service.buscarPorId(id);
    }

    @GetMapping
    public List<EventoPontuacaoDTO> listar() {

        return service.listar();
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {

        service.excluir(id);
    }

}