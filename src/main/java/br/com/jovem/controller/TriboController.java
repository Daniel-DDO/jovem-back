package br.com.jovem.controller;

import br.com.jovem.dto.TriboDTO;
import br.com.jovem.request.TriboRequest;
import br.com.jovem.service.TriboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tribos")
public class TriboController {

    @Autowired
    private TriboService service;

    @PostMapping
    public TriboDTO criar(@RequestBody @Valid TriboRequest request) {

        return service.criar(request);
    }

    @PutMapping("/{id}")
    public TriboDTO editar(@PathVariable UUID id,
                           @RequestBody @Valid TriboRequest request) {

        return service.editar(id, request);
    }

    @GetMapping("/{id}")
    public TriboDTO buscarPorId(@PathVariable UUID id) {

        return service.buscarPorId(id);
    }

    @GetMapping
    public List<TriboDTO> listar() {

        return service.listar();
    }

    @PatchMapping("/{id}/ativar")
    public void ativar(@PathVariable UUID id) {

        service.ativar(id);
    }

    @PatchMapping("/{id}/desativar")
    public void desativar(@PathVariable UUID id) {

        service.desativar(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {

        service.excluir(id);
    }

}