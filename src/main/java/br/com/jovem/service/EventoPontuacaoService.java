package br.com.jovem.service;

import br.com.jovem.dto.EventoPontuacaoDTO;
import br.com.jovem.enums.TipoDestinoPontuacao;
import br.com.jovem.model.EventoPontuacao;
import br.com.jovem.model.Missao;
import br.com.jovem.model.NacaoTemporada;
import br.com.jovem.model.Temporada;
import br.com.jovem.model.TriboTemporada;
import br.com.jovem.repository.EventoPontuacaoRepository;
import br.com.jovem.request.EventoPontuacaoRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventoPontuacaoService {

    @Autowired
    private EventoPontuacaoRepository repository;

    @Transactional
    public EventoPontuacaoDTO criar(EventoPontuacaoRequest request) {

        validarDestino(request);

        EventoPontuacao evento = new EventoPontuacao();

        Temporada temporada = new Temporada();
        temporada.setId(request.temporadaId());

        evento.setTemporada(temporada);

        evento.setDestino(request.destino());
        evento.setTipo(request.tipo());
        evento.setPontos(request.pontos());
        evento.setMotivo(request.motivo());

        if (request.triboTemporadaId() != null) {

            TriboTemporada triboTemporada = new TriboTemporada();
            triboTemporada.setId(request.triboTemporadaId());

            evento.setTriboTemporada(triboTemporada);
        }

        if (request.nacaoTemporadaId() != null) {

            NacaoTemporada nacaoTemporada = new NacaoTemporada();
            nacaoTemporada.setId(request.nacaoTemporadaId());

            evento.setNacaoTemporada(nacaoTemporada);
        }

        if (request.missaoId() != null) {

            Missao missao = new Missao();
            missao.setId(request.missaoId());

            evento.setMissao(missao);
        }

        EventoPontuacao salvo = repository.save(evento);

        return new EventoPontuacaoDTO(salvo);
    }

    @Transactional
    public void excluir(UUID id) {

        EventoPontuacao evento = buscarEntidade(id);

        repository.delete(evento);
    }

    @Transactional
    public EventoPontuacaoDTO buscarPorId(UUID id) {

        return new EventoPontuacaoDTO(
                buscarEntidade(id)
        );
    }

    @Transactional
    public List<EventoPontuacaoDTO> listar() {

        return repository.findAll()
                .stream()
                .map(EventoPontuacaoDTO::new)
                .toList();
    }

    private EventoPontuacao buscarEntidade(UUID id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Evento de pontuação não encontrado."
                        )
                );
    }

    private void validarDestino(EventoPontuacaoRequest request) {

        if (request.destino() == TipoDestinoPontuacao.TRIBO
                && request.triboTemporadaId() == null) {

            throw new IllegalArgumentException(
                    "Uma pontuação para tribo precisa possuir triboTemporadaId."
            );
        }

        if (request.destino() == TipoDestinoPontuacao.NACAO
                && request.nacaoTemporadaId() == null) {

            throw new IllegalArgumentException(
                    "Uma pontuação para nação precisa possuir nacaoTemporadaId."
            );
        }
    }

}