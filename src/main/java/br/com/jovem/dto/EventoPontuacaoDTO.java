package br.com.jovem.dto;

import br.com.jovem.enums.TipoDestinoPontuacao;
import br.com.jovem.enums.TipoPontuacao;
import br.com.jovem.model.EventoPontuacao;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EventoPontuacaoDTO(
        UUID id,

        UUID temporadaId,
        String nomeTemporada,

        TipoDestinoPontuacao destino,

        UUID triboTemporadaId,
        String nomeTribo,

        UUID nacaoTemporadaId,
        String nomeNacao,

        TipoPontuacao tipo,

        Integer pontos,

        String motivo,

        UUID missaoId,
        String nomeMissao,

        OffsetDateTime criadoEm

) {

    public EventoPontuacaoDTO(EventoPontuacao evento) {
        this(
                evento.getId(),

                evento.getTemporada().getId(),
                evento.getTemporada().getNome(),

                evento.getDestino(),

                evento.getTriboTemporada() != null
                        ? evento.getTriboTemporada().getId()
                        : null,

                evento.getTriboTemporada() != null
                        ? evento.getTriboTemporada().getTribo().getNome()
                        : null,

                evento.getNacaoTemporada() != null
                        ? evento.getNacaoTemporada().getId()
                        : null,

                evento.getNacaoTemporada() != null
                        ? evento.getNacaoTemporada().getNacao().getNome()
                        : null,

                evento.getTipo(),

                evento.getPontos(),

                evento.getMotivo(),

                evento.getMissao() != null
                        ? evento.getMissao().getId()
                        : null,

                evento.getMissao() != null
                        ? evento.getMissao().getNome()
                        : null,

                evento.getCriadoEm()
        );
    }

}