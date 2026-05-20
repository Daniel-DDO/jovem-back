package br.com.jovem.dto;

import br.com.jovem.model.Tribo;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TriboDTO(
        UUID id,
        String nome,
        String descricao,
        String logoUrl,
        String cor,
        boolean ativa,
        OffsetDateTime criadoEm

) {

    public TriboDTO(Tribo tribo) {
        this(
                tribo.getId(),
                tribo.getNome(),
                tribo.getDescricao(),
                tribo.getLogoUrl(),
                tribo.getCor(),
                tribo.isAtiva(),
                tribo.getCriadoEm()
        );
    }

}