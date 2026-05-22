package br.com.jovem.controller;

import br.com.jovem.dto.TriboDTO;
import br.com.jovem.request.TriboRequest;
import br.com.jovem.service.TriboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tribos")
public class TriboController {

    @Autowired
    private TriboService service;

    @PostMapping
    public ResponseEntity<TriboDTO> criar(@RequestBody @Valid TriboRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<TriboDTO>> criarEmLote(@RequestBody @Valid List<TriboRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarEmLote(requests));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TriboDTO> editar(@PathVariable UUID id, @RequestBody @Valid TriboRequest request) {
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
    public ResponseEntity<TriboDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TriboDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}