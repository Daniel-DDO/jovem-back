package br.com.jovem.service;

import br.com.jovem.dto.PessoaDTO;
import br.com.jovem.model.Pessoa;
import br.com.jovem.repository.PessoaRepository;
import br.com.jovem.request.PessoaRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Transactional
    public PessoaDTO criar(PessoaRequest request) {

        validarNomeUsuarioDuplicado(request.nomeUsuario());

        Pessoa pessoa = new Pessoa();

        pessoa.setNome(request.nome());
        pessoa.setNomeUsuario(request.nomeUsuario());
        pessoa.setTelefone(request.telefone());

        return new PessoaDTO(
                repository.save(pessoa)
        );
    }

    @Transactional
    public PessoaDTO editar(UUID id, PessoaRequest request) {

        Pessoa pessoa = buscarEntidade(id);

        boolean nomeUsuarioEmUso = repository.findAll()
                .stream()
                .anyMatch(p ->
                        !p.getId().equals(id)
                                && p.getNomeUsuario()
                                .equalsIgnoreCase(request.nomeUsuario())
                );

        if (nomeUsuarioEmUso) {
            throw new IllegalArgumentException(
                    "Nome de usuário já utilizado."
            );
        }

        pessoa.setNome(request.nome());
        pessoa.setNomeUsuario(request.nomeUsuario());
        pessoa.setTelefone(request.telefone());

        return new PessoaDTO(
                repository.save(pessoa)
        );
    }

    @Transactional
    public void ativar(UUID id) {

        Pessoa pessoa = buscarEntidade(id);

        if (pessoa.isAtiva()) {
            return;
        }

        pessoa.setAtiva(true);

        repository.save(pessoa);
    }

    @Transactional
    public void desativar(UUID id) {

        Pessoa pessoa = buscarEntidade(id);

        if (!pessoa.isAtiva()) {
            return;
        }

        pessoa.setAtiva(false);

        repository.save(pessoa);
    }

    @Transactional
    public void excluir(UUID id) {

        Pessoa pessoa = buscarEntidade(id);

        repository.delete(pessoa);
    }

    @Transactional
    public PessoaDTO buscarPorId(UUID id) {

        return new PessoaDTO(
                buscarEntidade(id)
        );
    }

    @Transactional
    public List<PessoaDTO> listar() {

        return repository.findAll()
                .stream()
                .map(PessoaDTO::new)
                .toList();
    }

    private Pessoa buscarEntidade(UUID id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Pessoa não encontrada."
                        )
                );
    }

    private void validarNomeUsuarioDuplicado(String nomeUsuario) {

        boolean existe = repository.findAll()
                .stream()
                .anyMatch(p ->
                        p.getNomeUsuario()
                                .equalsIgnoreCase(nomeUsuario)
                );

        if (existe) {
            throw new IllegalArgumentException(
                    "Nome de usuário já utilizado."
            );
        }
    }

}