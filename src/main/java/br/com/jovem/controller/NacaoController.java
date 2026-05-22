package br.com.jovem.controller;

import br.com.jovem.dto.NacaoDTO;
import br.com.jovem.request.NacaoRequest;
import br.com.jovem.service.NacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nacoes")
public class NacaoController {

    @Autowired
    private NacaoService service;

    @PostMapping
    public ResponseEntity<NacaoDTO> criar(@RequestBody @Valid NacaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<NacaoDTO>> criarEmLote(@RequestBody @Valid List<NacaoRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarEmLote(requests));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NacaoDTO> editar(@PathVariable UUID id, @RequestBody @Valid NacaoRequest request) {
        return ResponseEntity.ok(service.editar(id, request));
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

    @GetMapping("/{id}")
    public ResponseEntity<NacaoDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<NacaoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}