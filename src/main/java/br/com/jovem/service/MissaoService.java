package br.com.jovem.service;

import br.com.jovem.dto.MissaoDTO;
import br.com.jovem.model.Missao;
import br.com.jovem.repository.MissaoRepository;
import br.com.jovem.request.MissaoRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MissaoService {

    @Autowired
    private MissaoRepository repository;

    @Transactional
    public MissaoDTO criar(MissaoRequest request) {
        validarNomeDuplicado(request.nome());

        Missao missao = new Missao();
        missao.setNome(request.nome());
        missao.setDescricao(request.descricao());
        missao.setPontos(request.pontos());

        return new MissaoDTO(repository.save(missao));
    }

    @Transactional
    public List<MissaoDTO> criarEmLote(List<MissaoRequest> requests) {
        List<Missao> missoes = requests.stream().map(request -> {
            validarNomeDuplicado(request.nome());
            Missao missao = new Missao();
            missao.setNome(request.nome());
            missao.setDescricao(request.descricao());
            missao.setPontos(request.pontos());
            return missao;
        }).toList();

        return repository.saveAll(missoes).stream()
                .map(MissaoDTO::new)
                .toList();
    }

    @Transactional
    public MissaoDTO editar(UUID id, MissaoRequest request) {
        Missao missao = buscarEntidade(id);

        if (request.nome() != null && !request.nome().isBlank()) {
            if (repository.existsByNomeIgnoreCaseAndIdNot(request.nome(), id)) {
                throw new IllegalArgumentException("Já existe uma missão com esse nome.");
            }
            missao.setNome(request.nome());
        }

        if (request.descricao() != null) {
            missao.setDescricao(request.descricao());
        }

        if (request.pontos() != null) {
            missao.setPontos(request.pontos());
        }

        return new MissaoDTO(repository.save(missao));
    }

    @Transactional
    public void ativar(UUID id) {
        Missao missao = buscarEntidade(id);

        if (!missao.isAtiva()) {
            missao.setAtiva(true);
            repository.save(missao);
        }
    }

    @Transactional
    public void desativar(UUID id) {
        Missao missao = buscarEntidade(id);

        if (missao.isAtiva()) {
            missao.setAtiva(false);
            repository.save(missao);
        }
    }

    @Transactional
    public void excluir(UUID id) {
        Missao missao = buscarEntidade(id);
        repository.delete(missao);
    }

    @Transactional
    public MissaoDTO buscarPorId(UUID id) {
        return new MissaoDTO(buscarEntidade(id));
    }

    @Transactional
    public List<MissaoDTO> listar() {
        return repository.findAll().stream()
                .map(MissaoDTO::new)
                .toList();
    }

    private Missao buscarEntidade(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada."));
    }

    private void validarNomeDuplicado(String nome) {
        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new IllegalArgumentException("Já existe uma missão com esse nome.");
        }
    }
}