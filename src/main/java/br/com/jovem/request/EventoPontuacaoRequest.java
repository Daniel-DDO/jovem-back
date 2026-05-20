package br.com.jovem.request;

import br.com.jovem.enums.TipoDestinoPontuacao;
import br.com.jovem.enums.TipoPontuacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EventoPontuacaoRequest(

        @NotNull(message = "A temporada é obrigatória.")
        UUID temporadaId,

        @NotNull(message = "O destino é obrigatório.")
        TipoDestinoPontuacao destino,

        UUID triboTemporadaId,

        UUID nacaoTemporadaId,

        @NotNull(message = "O tipo da pontuação é obrigatório.")
        TipoPontuacao tipo,

        @NotNull(message = "A quantidade de pontos é obrigatória.")
        Integer pontos,

        @NotBlank(message = "O motivo é obrigatório.")
        String motivo,

        UUID missaoId

) {
}