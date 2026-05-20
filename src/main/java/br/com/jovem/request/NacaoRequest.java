package br.com.jovem.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NacaoRequest(
        @NotBlank(message = "O nome da nação é obrigatório.")
        @Size(max = 100)
        String nome,

        @Size(max = 500)
        String descricao,

        String logoUrl,

        @Size(max = 20)
        String cor

) {
}