package br.com.jovem.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record TemporadaRequest(
        @NotBlank(message = "O nome da temporada é obrigatório.")
        String nome,

        @NotNull(message = "A data inicial é obrigatória.")
        OffsetDateTime dataInicio,

        OffsetDateTime dataFim

) {
}