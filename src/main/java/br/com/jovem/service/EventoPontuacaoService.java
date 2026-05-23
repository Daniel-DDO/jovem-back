package br.com.jovem.service;

import br.com.jovem.dto.EventoPontuacaoDTO;
import br.com.jovem.enums.TipoDestinoPontuacao;
import br.com.jovem.model.EventoPontuacao;
import br.com.jovem.model.Missao;
import br.com.jovem.model.NacaoTemporada;
import br.com.jovem.model.Temporada;
import br.com.jovem.model.TriboTemporada;
import br.com.jovem.repository.*;
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

    @Autowired
    private TriboTemporadaRepository triboTemporadaRepository;

    @Autowired
    private NacaoTemporadaRepository nacaoTemporadaRepository;

    @Autowired
    private TemporadaRepository temporadaRepository;

    @Transactional
    public EventoPontuacaoDTO criar(EventoPontuacaoRequest request) {
        validarDestino(request);

        EventoPontuacao evento = new EventoPontuacao();

        Temporada temporada = temporadaRepository.findById(request.temporadaId())
                .orElseThrow(() -> new EntityNotFoundException("Temporada não encontrada com o ID fornecido."));
        evento.setTemporada(temporada);

        evento.setDestino(request.destino());
        evento.setTipo(request.tipo());
        evento.setMotivo(request.motivo());

        int multiplicador = request.multiplicador() != null ? request.multiplicador() : 1;
        evento.setMultiplicador(multiplicador);

        int pontosCalculados = request.pontos() * multiplicador;
        evento.setPontos(pontosCalculados);

        if (request.triboTemporadaId() != null) {
            TriboTemporada triboTemporada = triboTemporadaRepository.findById(request.triboTemporadaId())
                    .orElseThrow(() -> new EntityNotFoundException("Tribo da Temporada não encontrada com o ID fornecido."));

            evento.setTriboTemporada(triboTemporada);
            triboTemporada.setTotalPontos(triboTemporada.getTotalPontos()+pontosCalculados);
        }

        if (request.nacaoTemporadaId() != null) {
            NacaoTemporada nacaoTemporada = nacaoTemporadaRepository.findById(request.nacaoTemporadaId())
                    .orElseThrow(() -> new EntityNotFoundException("Nação da Temporada não encontrada com o ID fornecido."));

            evento.setNacaoTemporada(nacaoTemporada);
            nacaoTemporada.setTotalPontos(nacaoTemporada.getTotalPontos()+pontosCalculados);
        }

        if (request.missaoId() != null) {
            Missao missao = new Missao();
            missao.setId(request.missaoId());
            evento.setMissao(missao);
        }

        return new EventoPontuacaoDTO(repository.save(evento));
    }

    @Transactional
    public void excluir(UUID id) {
        EventoPontuacao evento = buscarEntidade(id);
        int pontosAEstornar = evento.getPontos();

        if (evento.getTriboTemporada() != null) {
            TriboTemporada tribo = evento.getTriboTemporada();
            int saldoAtual = tribo.getTotalPontos() != null ? tribo.getTotalPontos() : 0;

            tribo.setTotalPontos(saldoAtual - pontosAEstornar);
        }

        if (evento.getNacaoTemporada() != null) {
            NacaoTemporada nacao = evento.getNacaoTemporada();
            int saldoAtual = nacao.getTotalPontos() != null ? nacao.getTotalPontos() : 0;

            nacao.setTotalPontos(saldoAtual - pontosAEstornar);
        }

        repository.delete(evento);
    }

    @Transactional
    public EventoPontuacaoDTO buscarPorId(UUID id) {
        return new EventoPontuacaoDTO(buscarEntidade(id));
    }

    @Transactional
    public List<EventoPontuacaoDTO> listar() {
        return repository.findAll().stream()
                .map(EventoPontuacaoDTO::new)
                .toList();
    }

    @Transactional
    public List<EventoPontuacaoDTO> listarPorTemporada(UUID temporadaId) {
        return repository.findByTemporadaId(temporadaId).stream()
                .map(EventoPontuacaoDTO::new)
                .toList();
    }

    @Transactional
    public List<EventoPontuacaoDTO> listarPorNacaoTemporada(UUID nacaoTemporadaId) {
        return repository.findByNacaoTemporadaId(nacaoTemporadaId).stream()
                .map(EventoPontuacaoDTO::new)
                .toList();
    }

    @Transactional
    public List<EventoPontuacaoDTO> listarPorTriboTemporada(UUID triboTemporadaId) {
        return repository.findByTriboTemporadaId(triboTemporadaId).stream()
                .map(EventoPontuacaoDTO::new)
                .toList();
    }

    private EventoPontuacao buscarEntidade(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento de pontuação não encontrado."));
    }

    private void validarDestino(EventoPontuacaoRequest request) {
        if (request.destino() == TipoDestinoPontuacao.TRIBO && request.triboTemporadaId() == null) {
            throw new IllegalArgumentException("Uma pontuação para tribo precisa possuir triboTemporadaId.");
        }

        if (request.destino() == TipoDestinoPontuacao.NACAO && request.nacaoTemporadaId() == null) {
            throw new IllegalArgumentException("Uma pontuação para nação precisa possuir nacaoTemporadaId.");
        }
    }
}