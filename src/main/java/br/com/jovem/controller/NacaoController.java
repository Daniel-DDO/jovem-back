package br.com.jovem.controller;

import br.com.jovem.dto.NacaoDTO;
import br.com.jovem.request.NacaoRequest;
import br.com.jovem.service.NacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/nacoes")
public class NacaoController {

    @Autowired
    private NacaoService service;

    @PostMapping
    public NacaoDTO criar(@RequestBody @Valid NacaoRequest request) {

        return service.criar(request);
    }

    @PutMapping("/{id}")
    public NacaoDTO editar(@PathVariable UUID id,
                           @RequestBody @Valid NacaoRequest request) {

        return service.editar(id, request);
    }

    @GetMapping("/{id}")
    public NacaoDTO buscarPorId(@PathVariable UUID id) {

        return service.buscarPorId(id);
    }

    @GetMapping
    public List<NacaoDTO> listar() {

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