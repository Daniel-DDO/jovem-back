package br.com.jovem.service;

import br.com.jovem.dto.TriboTemporadaDTO;
import br.com.jovem.model.NacaoTemporada;
import br.com.jovem.model.Pessoa;
import br.com.jovem.model.Temporada;
import br.com.jovem.model.Tribo;
import br.com.jovem.model.TriboTemporada;
import br.com.jovem.repository.TriboTemporadaRepository;
import br.com.jovem.request.TriboTemporadaRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TriboTemporadaService {

    @Autowired
    private TriboTemporadaRepository repository;

    @Transactional
    public TriboTemporadaDTO criar(TriboTemporadaRequest request) {

        boolean existe = repository.findAll()
                .stream()
                .anyMatch(tt ->
                        tt.getTribo().getId().equals(request.triboId())
                                && tt.getTemporada().getId().equals(request.temporadaId())
                );

        if (existe) {
            throw new IllegalArgumentException(
                    "Essa tribo já está vinculada nessa temporada."
            );
        }

        TriboTemporada entidade = new TriboTemporada();

        Tribo tribo = new Tribo();
        tribo.setId(request.triboId());

        Temporada temporada = new Temporada();
        temporada.setId(request.temporadaId());

        entidade.setTribo(tribo);
        entidade.setTemporada(temporada);

        if (request.nacaoTemporadaId() != null) {

            NacaoTemporada nacaoTemporada = new NacaoTemporada();
            nacaoTemporada.setId(request.nacaoTemporadaId());

            entidade.setNacaoTemporada(nacaoTemporada);
        }

        if (request.liderId() != null) {

            Pessoa pessoa = new Pessoa();
            pessoa.setId(request.liderId());

            entidade.setLider(pessoa);
        }

        return new TriboTemporadaDTO(
                repository.save(entidade)
        );
    }

    @Transactional
    public TriboTemporadaDTO editar(UUID id, TriboTemporadaRequest request) {

        TriboTemporada entidade = buscarEntidade(id);

        if (request.nacaoTemporadaId() != null) {

            NacaoTemporada nacaoTemporada = new NacaoTemporada();
            nacaoTemporada.setId(request.nacaoTemporadaId());

            entidade.setNacaoTemporada(nacaoTemporada);

        } else {
            entidade.setNacaoTemporada(null);
        }

        if (request.liderId() != null) {

            Pessoa pessoa = new Pessoa();
            pessoa.setId(request.liderId());

            entidade.setLider(pessoa);

        } else {
            entidade.setLider(null);
        }

        return new TriboTemporadaDTO(
                repository.save(entidade)
        );
    }

    @Transactional
    public void excluir(UUID id) {

        TriboTemporada entidade = buscarEntidade(id);

        repository.delete(entidade);
    }

    @Transactional
    public TriboTemporadaDTO buscarPorId(UUID id) {

        return new TriboTemporadaDTO(
                buscarEntidade(id)
        );
    }

    @Transactional
    public List<TriboTemporadaDTO> listar() {

        return repository.findAll()
                .stream()
                .map(TriboTemporadaDTO::new)
                .toList();
    }

    private TriboTemporada buscarEntidade(UUID id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Tribo da temporada não encontrada."
                        )
                );
    }

}