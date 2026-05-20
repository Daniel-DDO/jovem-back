package br.com.jovem.repository;

import br.com.jovem.model.EventoPontuacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventoPontuacaoRepository extends JpaRepository<EventoPontuacao, UUID> {

}
