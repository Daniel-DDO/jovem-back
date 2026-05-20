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

        Missao missao = new Missao();

        missao.setNome(request.nome());
        missao.setDescricao(request.descricao());
        missao.setPontos(request.pontos());

        return new MissaoDTO(
                repository.save(missao)
        );
    }

    @Transactional
    public MissaoDTO editar(UUID id, MissaoRequest request) {

        Missao missao = buscarEntidade(id);

        missao.setNome(request.nome());
        missao.setDescricao(request.descricao());
        missao.setPontos(request.pontos());

        return new MissaoDTO(
                repository.save(missao)
        );
    }

    @Transactional
    public void ativar(UUID id) {

        Missao missao = buscarEntidade(id);

        if (missao.isAtiva()) {
            return;
        }

        missao.setAtiva(true);

        repository.save(missao);
    }

    @Transactional
    public void desativar(UUID id) {

        Missao missao = buscarEntidade(id);

        if (!missao.isAtiva()) {
            return;
        }

        missao.setAtiva(false);

        repository.save(missao);
    }

    @Transactional
    public void excluir(UUID id) {

        Missao missao = buscarEntidade(id);

        repository.delete(missao);
    }

    @Transactional
    public MissaoDTO buscarPorId(UUID id) {

        return new MissaoDTO(
                buscarEntidade(id)
        );
    }

    @Transactional
    public List<MissaoDTO> listar() {

        return repository.findAll()
                .stream()
                .map(MissaoDTO::new)
                .toList();
    }

    private Missao buscarEntidade(UUID id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Missão não encontrada."
                        )
                );
    }

}