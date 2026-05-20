package br.com.jovem.dto;

import br.com.jovem.model.NacaoTemporada;

import java.time.OffsetDateTime;
import java.util.UUID;

public record NacaoTemporadaDTO(
        UUID id,
        UUID nacaoId,
        String nomeNacao,
        UUID temporadaId,
        String nomeTemporada,
        PessoaDTO lider,
        Integer totalPontos,
        OffsetDateTime criadoEm

) {

    public NacaoTemporadaDTO(NacaoTemporada entidade) {
        this(
                entidade.getId(),
                entidade.getNacao().getId(),
                entidade.getNacao().getNome(),
                entidade.getTemporada().getId(),
                entidade.getTemporada().getNome(),
                entidade.getLider() != null
                        ? new PessoaDTO(entidade.getLider())
                        : null,
                entidade.getTotalPontos(),
                entidade.getCriadoEm()
        );
    }

}