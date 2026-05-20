package br.com.jovem.dto;

import br.com.jovem.model.Missao;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MissaoDTO(
        UUID id,
        String nome,
        String descricao,
        Integer pontos,
        boolean ativa,
        OffsetDateTime criadoEm

) {

    public MissaoDTO(Missao missao) {
        this(
                missao.getId(),
                missao.getNome(),
                missao.getDescricao(),
                missao.getPontos(),
                missao.isAtiva(),
                missao.getCriadoEm()
        );
    }

}