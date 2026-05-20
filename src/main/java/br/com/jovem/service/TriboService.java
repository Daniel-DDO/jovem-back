package br.com.jovem.service;

import br.com.jovem.dto.TriboDTO;
import br.com.jovem.model.Tribo;
import br.com.jovem.repository.TriboRepository;
import br.com.jovem.request.TriboRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TriboService {

    @Autowired
    private TriboRepository repository;

    @Transactional
    public TriboDTO criar(TriboRequest request) {

        validarNomeDuplicado(request.nome());

        Tribo tribo = new Tribo();

        tribo.setNome(request.nome());
        tribo.setDescricao(request.descricao());
        tribo.setLogoUrl(request.logoUrl());
        tribo.setCor(request.cor());

        return new TriboDTO(
                repository.save(tribo)
        );
    }

    @Transactional
    public TriboDTO editar(UUID id, TriboRequest request) {

        Tribo tribo = buscarEntidade(id);

        boolean nomeEmUso = repository.findAll()
                .stream()
                .anyMatch(t ->
                        !t.getId().equals(id)
                                && t.getNome().equalsIgnoreCase(request.nome())
                );

        if (nomeEmUso) {
            throw new IllegalArgumentException(
                    "Já existe uma tribo com esse nome."
            );
        }

        tribo.setNome(request.nome());
        tribo.setDescricao(request.descricao());
        tribo.setLogoUrl(request.logoUrl());
        tribo.setCor(request.cor());

        return new TriboDTO(
                repository.save(tribo)
        );
    }

    @Transactional
    public void ativar(UUID id) {

        Tribo tribo = buscarEntidade(id);

        if (tribo.isAtiva()) {
            return;
        }

        tribo.setAtiva(true);

        repository.save(tribo);
    }

    @Transactional
    public void desativar(UUID id) {

        Tribo tribo = buscarEntidade(id);

        if (!tribo.isAtiva()) {
            return;
        }

        tribo.setAtiva(false);

        repository.save(tribo);
    }

    @Transactional
    public void excluir(UUID id) {

        Tribo tribo = buscarEntidade(id);

        repository.delete(tribo);
    }

    @Transactional
    public TriboDTO buscarPorId(UUID id) {

        return new TriboDTO(
                buscarEntidade(id)
        );
    }

    @Transactional
    public List<TriboDTO> listar() {

        return repository.findAll()
                .stream()
                .map(TriboDTO::new)
                .toList();
    }

    private Tribo buscarEntidade(UUID id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Tribo não encontrada."
                        )
                );
    }

    private void validarNomeDuplicado(String nome) {

        boolean existe = repository.findAll()
                .stream()
                .anyMatch(t ->
                        t.getNome().equalsIgnoreCase(nome)
                );

        if (existe) {
            throw new IllegalArgumentException(
                    "Já existe uma tribo com esse nome."
            );
        }
    }

}