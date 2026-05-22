package br.com.jovem.service;

import br.com.jovem.dto.NacaoDTO;
import br.com.jovem.model.Nacao;
import br.com.jovem.repository.NacaoRepository;
import br.com.jovem.request.NacaoRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NacaoService {

    @Autowired
    private NacaoRepository repository;

    @Transactional
    public NacaoDTO criar(NacaoRequest request) {
        validarNomeDuplicado(request.nome());

        Nacao nacao = new Nacao();
        nacao.setNome(request.nome());
        nacao.setDescricao(request.descricao());
        nacao.setLogoUrl(request.logoUrl());
        nacao.setCor(request.cor());

        return new NacaoDTO(repository.save(nacao));
    }

    @Transactional
    public List<NacaoDTO> criarEmLote(List<NacaoRequest> requests) {
        List<Nacao> nacoes = requests.stream().map(request -> {
            validarNomeDuplicado(request.nome());
            Nacao nacao = new Nacao();
            nacao.setNome(request.nome());
            nacao.setDescricao(request.descricao());
            nacao.setLogoUrl(request.logoUrl());
            nacao.setCor(request.cor());
            return nacao;
        }).toList();

        return repository.saveAll(nacoes).stream()
                .map(NacaoDTO::new)
                .toList();
    }

    @Transactional
    public NacaoDTO editar(UUID id, NacaoRequest request) {
        Nacao nacao = buscarEntidade(id);

        if (request.nome() != null) {
            if (repository.existsByNomeIgnoreCaseAndIdNot(request.nome(), id)) {
                throw new IllegalArgumentException("Já existe uma nação com esse nome.");
            }
            nacao.setNome(request.nome());
        }

        if (request.descricao() != null) {
            nacao.setDescricao(request.descricao());
        }
        if (request.logoUrl() != null) {
            nacao.setLogoUrl(request.logoUrl());
        }
        if (request.cor() != null) {
            nacao.setCor(request.cor());
        }

        return new NacaoDTO(repository.save(nacao));
    }

    @Transactional
    public void ativar(UUID id) {
        Nacao nacao = buscarEntidade(id);

        if (!nacao.isAtiva()) {
            nacao.setAtiva(true);
            repository.save(nacao);
        }
    }

    @Transactional
    public void desativar(UUID id) {
        Nacao nacao = buscarEntidade(id);

        if (nacao.isAtiva()) {
            nacao.setAtiva(false);
            repository.save(nacao);
        }
    }

    @Transactional
    public void excluir(UUID id) {
        Nacao nacao = buscarEntidade(id);
        repository.delete(nacao);
    }

    @Transactional
    public NacaoDTO buscarPorId(UUID id) {
        return new NacaoDTO(buscarEntidade(id));
    }

    @Transactional
    public List<NacaoDTO> listar() {
        return repository.findAll().stream()
                .map(NacaoDTO::new)
                .toList();
    }

    private Nacao buscarEntidade(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nação não encontrada."));
    }

    private void validarNomeDuplicado(String nome) {
        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new IllegalArgumentException("Já existe uma nação com esse nome.");
        }
    }
}