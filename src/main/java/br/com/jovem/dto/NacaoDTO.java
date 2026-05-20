package br.com.jovem.dto;

import br.com.jovem.model.Nacao;

import java.time.OffsetDateTime;
import java.util.UUID;

public record NacaoDTO(
        UUID id,
        String nome,
        String descricao,
        String logoUrl,
        String cor,
        boolean ativa,
        OffsetDateTime criadoEm

) {

    public NacaoDTO(Nacao nacao) {
        this(
                nacao.getId(),
                nacao.getNome(),
                nacao.getDescricao(),
                nacao.getLogoUrl(),
                nacao.getCor(),
                nacao.isAtiva(),
                nacao.getCriadoEm()
        );
    }

}