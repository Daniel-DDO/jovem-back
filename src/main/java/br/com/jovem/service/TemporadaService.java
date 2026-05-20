package br.com.jovem.service;

import br.com.jovem.dto.TemporadaDTO;
import br.com.jovem.model.Temporada;
import br.com.jovem.repository.TemporadaRepository;
import br.com.jovem.request.TemporadaRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class TemporadaService {

    @Autowired
    private TemporadaRepository repository;

    @Transactional
    public TemporadaDTO criar(TemporadaRequest request) {

        validarDatas(request);

        Temporada temporada = new Temporada();

        temporada.setNome(request.nome());
        temporada.setDataInicio(request.dataInicio());
        temporada.setDataFim(request.dataFim());

        return new TemporadaDTO(
                repository.save(temporada)
        );
    }

    @Transactional
    public TemporadaDTO editar(UUID id, TemporadaRequest request) {

        Temporada temporada = buscarEntidade(id);

        validarDatas(request);

        temporada.setNome(request.nome());
        temporada.setDataInicio(request.dataInicio());
        temporada.setDataFim(request.dataFim());

        return new TemporadaDTO(
                repository.save(temporada)
        );
    }

    @Transactional
    public void iniciar(UUID id) {

        Temporada temporada = buscarEntidade(id);

        if (temporada.isAtiva()) {
            return;
        }

        temporada.setAtiva(true);

        if (temporada.getDataInicio() == null) {
            temporada.setDataInicio(
                    OffsetDateTime.now(ZoneOffset.UTC)
            );
        }

        repository.save(temporada);
    }

    @Transactional
    public void encerrar(UUID id) {

        Temporada temporada = buscarEntidade(id);

        if (!temporada.isAtiva()) {
            return;
        }

        temporada.setAtiva(false);

        temporada.setDataFim(
                OffsetDateTime.now(ZoneOffset.UTC)
        );

        repository.save(temporada);
    }

    @Transactional
    public void excluir(UUID id) {

        Temporada temporada = buscarEntidade(id);

        repository.delete(temporada);
    }

    @Transactional
    public TemporadaDTO buscarPorId(UUID id) {

        return new TemporadaDTO(
                buscarEntidade(id)
        );
    }

    @Transactional
    public List<TemporadaDTO> listar() {

        return repository.findAll()
                .stream()
                .map(TemporadaDTO::new)
                .toList();
    }

    private Temporada buscarEntidade(UUID id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Temporada não encontrada."
                        )
                );
    }

    private void validarDatas(TemporadaRequest request) {

        if (request.dataFim() != null
                && request.dataFim().isBefore(request.dataInicio())) {

            throw new IllegalArgumentException(
                    "A data final não pode ser anterior à inicial."
            );
        }
    }

}