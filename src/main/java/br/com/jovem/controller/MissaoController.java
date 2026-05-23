package br.com.jovem.controller;

import br.com.jovem.dto.MissaoDTO;
import br.com.jovem.request.MissaoRequest;
import br.com.jovem.service.MissaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    @Autowired
    private MissaoService service;

    @PostMapping
    public ResponseEntity<MissaoDTO> criar(@RequestBody @Valid MissaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<MissaoDTO>> criarEmLote(@RequestBody @Valid List<MissaoRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarEmLote(requests));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissaoDTO> editar(@PathVariable UUID id, @RequestBody MissaoRequest request) {
        return ResponseEntity.ok(service.editar(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<MissaoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable UUID id) {
        service.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable UUID id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}