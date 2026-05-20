package br.com.jovem.dto;

import br.com.jovem.model.Pessoa;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PessoaDTO(
        UUID id,
        String nome,
        String nomeUsuario,
        String telefone,
        boolean ativa,
        OffsetDateTime criadoEm,
        Boolean contaRequisitada

) {

    public PessoaDTO(Pessoa pessoa) {
        this(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getNomeUsuario(),
                pessoa.getTelefone(),
                pessoa.isAtiva(),
                pessoa.getCriadoEm(),
                pessoa.getContaRequisitada()
        );
    }

}