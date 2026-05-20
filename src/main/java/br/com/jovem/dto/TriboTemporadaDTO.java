package br.com.jovem.dto;

import br.com.jovem.model.TriboTemporada;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TriboTemporadaDTO(
        UUID id,
        UUID triboId,
        String nomeTribo,
        UUID temporadaId,
        String nomeTemporada,
        UUID nacaoTemporadaId,
        String nomeNacao,
        PessoaDTO lider,
        Integer totalPontos,
        OffsetDateTime criadoEm

) {

    public TriboTemporadaDTO(TriboTemporada entidade) {
        this(
                entidade.getId(),
                entidade.getTribo().getId(),
                entidade.getTribo().getNome(),
                entidade.getTemporada().getId(),
                entidade.getTemporada().getNome(),
                entidade.getNacaoTemporada() != null
                        ? entidade.getNacaoTemporada().getId()
                        : null,
                entidade.getNacaoTemporada() != null
                        ? entidade.getNacaoTemporada().getNacao().getNome()
                        : null,
                entidade.getLider() != null
                        ? new PessoaDTO(entidade.getLider())
                        : null,
                entidade.getTotalPontos(),
                entidade.getCriadoEm()
        );
    }

}