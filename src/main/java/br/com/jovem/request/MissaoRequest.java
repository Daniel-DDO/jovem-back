package br.com.jovem.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MissaoRequest(
        @NotBlank(message = "O nome da missão é obrigatório.")
        String nome,

        String descricao,

        @NotNull(message = "A pontuação é obrigatória.")
        @Positive(message = "A pontuação deve ser positiva.")
        Integer pontos

) {
}