package br.com.jovem.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PessoaRequest(
        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "O nome de usuário é obrigatório.")
        @Size(max = 150)
        String nomeUsuario,

        @Size(max = 30)
        String telefone,

        String senha

) {
}