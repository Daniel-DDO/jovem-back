package br.com.jovem.controller;

import br.com.jovem.dto.MissaoDTO;
import br.com.jovem.request.MissaoRequest;
import br.com.jovem.service.MissaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    @Autowired
    private MissaoService service;

    @PostMapping
    public MissaoDTO criar(@RequestBody @Valid MissaoRequest request) {

        return service.criar(request);
    }

    @PutMapping("/{id}")
    public MissaoDTO editar(@PathVariable UUID id,
                            @RequestBody @Valid MissaoRequest request) {

        return service.editar(id, request);
    }

    @GetMapping("/{id}")
    public MissaoDTO buscarPorId(@PathVariable UUID id) {

        return service.buscarPorId(id);
    }

    @GetMapping
    public List<MissaoDTO> listar() {

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