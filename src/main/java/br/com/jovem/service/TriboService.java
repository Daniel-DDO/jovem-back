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

        return new TriboDTO(repository.save(tribo));
    }

    @Transactional
    public List<TriboDTO> criarEmLote(List<TriboRequest> requests) {
        List<Tribo> tribos = requests.stream().map(request -> {
            validarNomeDuplicado(request.nome());
            Tribo tribo = new Tribo();
            tribo.setNome(request.nome());
            tribo.setDescricao(request.descricao());
            tribo.setLogoUrl(request.logoUrl());
            tribo.setCor(request.cor());
            return tribo;
        }).toList();

        return repository.saveAll(tribos).stream()
                .map(TriboDTO::new)
                .toList();
    }

    @Transactional
    public TriboDTO editar(UUID id, TriboRequest request) {
        Tribo tribo = buscarEntidade(id);

        if (request.nome() != null) {
            if (repository.existsByNomeIgnoreCaseAndIdNot(request.nome(), id)) {
                throw new IllegalArgumentException("Já existe uma tribo com esse nome.");
            }
            tribo.setNome(request.nome());
        }

        if (request.descricao() != null) {
            tribo.setDescricao(request.descricao());
        }
        if (request.logoUrl() != null) {
            tribo.setLogoUrl(request.logoUrl());
        }
        if (request.cor() != null) {
            tribo.setCor(request.cor());
        }

        return new TriboDTO(repository.save(tribo));
    }

    @Transactional
    public void ativar(UUID id) {
        Tribo tribo = buscarEntidade(id);

        if (!tribo.isAtiva()) {
            tribo.setAtiva(true);
            repository.save(tribo);
        }
    }

    @Transactional
    public void desativar(UUID id) {
        Tribo tribo = buscarEntidade(id);

        if (tribo.isAtiva()) {
            tribo.setAtiva(false);
            repository.save(tribo);
        }
    }

    @Transactional
    public void excluir(UUID id) {
        Tribo tribo = buscarEntidade(id);
        repository.delete(tribo);
    }

    @Transactional
    public TriboDTO buscarPorId(UUID id) {
        return new TriboDTO(buscarEntidade(id));
    }

    @Transactional
    public List<TriboDTO> listar() {
        return repository.findAll().stream()
                .map(TriboDTO::new)
                .toList();
    }

    private Tribo buscarEntidade(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tribo não encontrada."));
    }

    private void validarNomeDuplicado(String nome) {
        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new IllegalArgumentException("Já existe uma tribo com esse nome.");
        }
    }
}