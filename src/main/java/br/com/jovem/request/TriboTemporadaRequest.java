package br.com.jovem.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TriboTemporadaRequest(
        @NotNull(message = "A tribo é obrigatória.")
        UUID triboId,

        @NotNull(message = "A temporada é obrigatória.")
        UUID temporadaId,

        UUID nacaoTemporadaId,

        UUID liderId

) {
}