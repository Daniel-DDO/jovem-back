package br.com.jovem.request;

import jakarta.validation.constraints.Size;

public record NacaoRequest(
        @Size(max = 100)
        String nome,

        @Size(max = 500)
        String descricao,

        String logoUrl,

        @Size(max = 20)
        String cor

) {
}