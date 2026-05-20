package br.com.jovem.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NacaoTemporadaRequest(
        @NotNull(message = "A nação é obrigatória.")
        UUID nacaoId,

        @NotNull(message = "A temporada é obrigatória.")
        UUID temporadaId,

        UUID liderId

) {
}