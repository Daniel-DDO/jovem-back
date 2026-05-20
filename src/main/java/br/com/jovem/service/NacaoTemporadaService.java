package br.com.jovem.service;

import br.com.jovem.dto.NacaoTemporadaDTO;
import br.com.jovem.model.Nacao;
import br.com.jovem.model.NacaoTemporada;
import br.com.jovem.model.Pessoa;
import br.com.jovem.model.Temporada;
import br.com.jovem.repository.NacaoRepository;
import br.com.jovem.repository.NacaoTemporadaRepository;
import br.com.jovem.repository.PessoaRepository;
import br.com.jovem.repository.TemporadaRepository;
import br.com.jovem.request.NacaoTemporadaRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NacaoTemporadaService {

    @Autowired
    private NacaoTemporadaRepository repository;

    @Autowired
    private NacaoRepository nacaoRepository;

    @Autowired
    private TemporadaRepository temporadaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public NacaoTemporadaDTO criar(NacaoTemporadaRequest request) {

        Nacao nacao = buscarNacao(request.nacaoId());

        Temporada temporada = buscarTemporada(request.temporadaId());

        boolean existe = repository.findAll()
                .stream()
                .anyMatch(nacaoTemporada ->
                        nacaoTemporada.getNacao().getId().equals(request.nacaoId())
                                && nacaoTemporada.getTemporada().getId().equals(request.temporadaId())
                );

        if (existe) {
            throw new IllegalArgumentException(
                    "Essa nação já está cadastrada nessa temporada."
            );
        }

        NacaoTemporada entidade = new NacaoTemporada();

        entidade.setNacao(nacao);
        entidade.setTemporada(temporada);
        entidade.setTotalPontos(0);

        if (request.liderId() != null) {

            Pessoa lider = buscarPessoa(request.liderId());

            entidade.setLider(lider);
        }

        NacaoTemporada salvo = repository.save(entidade);

        return new NacaoTemporadaDTO(salvo);
    }

    @Transactional
    public NacaoTemporadaDTO editar(UUID id,
                                    NacaoTemporadaRequest request) {

        NacaoTemporada entidade = buscarEntidade(id);

        if (request.liderId() != null) {

            Pessoa lider = buscarPessoa(request.liderId());

            entidade.setLider(lider);

        } else {

            entidade.setLider(null);
        }

        NacaoTemporada salvo = repository.save(entidade);

        return new NacaoTemporadaDTO(salvo);
    }

    @Transactional
    public void excluir(UUID id) {

        NacaoTemporada entidade = buscarEntidade(id);

        repository.delete(entidade);
    }

    @Transactional
    public NacaoTemporadaDTO buscarPorId(UUID id) {

        return new NacaoTemporadaDTO(
                buscarEntidade(id)
        );
    }

    @Transactional
    public List<NacaoTemporadaDTO> listar() {

        return repository.findAll()
                .stream()
                .map(NacaoTemporadaDTO::new)
                .toList();
    }

    private NacaoTemporada buscarEntidade(UUID id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Nação da temporada não encontrada."
                        )
                );
    }

    private Nacao buscarNacao(UUID id) {

        return nacaoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Nação não encontrada."
                        )
                );
    }

    private Temporada buscarTemporada(UUID id) {

        return temporadaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Temporada não encontrada."
                        )
                );
    }

    private Pessoa buscarPessoa(UUID id) {

        return pessoaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Pessoa não encontrada."
                        )
                );
    }

}