package br.com.jovem.dto;

import br.com.jovem.enums.TipoDestinoPontuacao;
import br.com.jovem.enums.TipoPontuacao;
import br.com.jovem.model.EventoPontuacao;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EventoPontuacaoDTO(
        UUID id,
        UUID temporadaId,
        TipoDestinoPontuacao destino,
        UUID triboTemporadaId,
        UUID nacaoTemporadaId,
        TipoPontuacao tipo,
        Integer pontos,
        Integer multiplicador,
        String motivo,
        UUID missaoId,
        OffsetDateTime criadoEm
) {
    public EventoPontuacaoDTO(EventoPontuacao model) {
        this(
                model.getId(),
                model.getTemporada().getId(),
                model.getDestino(),
                model.getTriboTemporada() != null ? model.getTriboTemporada().getId() : null,
                model.getNacaoTemporada() != null ? model.getNacaoTemporada().getId() : null,
                model.getTipo(),
                model.getPontos(),
                model.getMultiplicador(),
                model.getMotivo(),
                model.getMissao() != null ? model.getMissao().getId() : null,
                model.getCriadoEm()
        );
    }
}