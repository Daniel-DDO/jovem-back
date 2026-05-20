package br.com.jovem.dto;

import br.com.jovem.model.Temporada;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TemporadaDTO(
        UUID id,
        String nome,
        OffsetDateTime dataInicio,
        OffsetDateTime dataFim,
        boolean ativa,
        OffsetDateTime criadoEm

) {

    public TemporadaDTO(Temporada temporada) {
        this(
                temporada.getId(),
                temporada.getNome(),
                temporada.getDataInicio(),
                temporada.getDataFim(),
                temporada.isAtiva(),
                temporada.getCriadoEm()
        );
    }

}