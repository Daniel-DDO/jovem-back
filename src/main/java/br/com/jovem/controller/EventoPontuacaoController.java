package br.com.jovem.controller;

import br.com.jovem.dto.EventoPontuacaoDTO;
import br.com.jovem.request.EventoPontuacaoRequest;
import br.com.jovem.service.EventoPontuacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eventos-pontuacao")
public class EventoPontuacaoController {

    @Autowired
    private EventoPontuacaoService service;

    @PostMapping
    public ResponseEntity<EventoPontuacaoDTO> criar(@RequestBody @Valid EventoPontuacaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoPontuacaoDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EventoPontuacaoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/temporada/{temporadaId}")
    public ResponseEntity<List<EventoPontuacaoDTO>> listarPorTemporada(@PathVariable UUID temporadaId) {
        return ResponseEntity.ok(service.listarPorTemporada(temporadaId));
    }

    @GetMapping("/nacao-temporada/{nacaoTemporadaId}")
    public ResponseEntity<List<EventoPontuacaoDTO>> listarPorNacaoTemporada(@PathVariable UUID nacaoTemporadaId) {
        return ResponseEntity.ok(service.listarPorNacaoTemporada(nacaoTemporadaId));
    }

    @GetMapping("/tribo-temporada/{triboTemporadaId}")
    public ResponseEntity<List<EventoPontuacaoDTO>> listarPorTriboTemporada(@PathVariable UUID triboTemporadaId) {
        return ResponseEntity.ok(service.listarPorTriboTemporada(triboTemporadaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}