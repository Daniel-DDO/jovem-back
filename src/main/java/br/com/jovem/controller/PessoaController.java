package br.com.jovem.controller;

import br.com.jovem.dto.PessoaDTO;
import br.com.jovem.request.PessoaRequest;
import br.com.jovem.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @PostMapping
    public PessoaDTO criar(@RequestBody @Valid PessoaRequest request) {

        return service.criar(request);
    }

    @PutMapping("/{id}")
    public PessoaDTO editar(@PathVariable UUID id,
                            @RequestBody @Valid PessoaRequest request) {

        return service.editar(id, request);
    }

    @GetMapping("/{id}")
    public PessoaDTO buscarPorId(@PathVariable UUID id) {

        return service.buscarPorId(id);
    }

    @GetMapping
    public List<PessoaDTO> listar() {

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