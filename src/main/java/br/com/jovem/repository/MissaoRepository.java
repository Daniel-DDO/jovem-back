package br.com.jovem.repository;

import br.com.jovem.model.Missao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, UUID> {
}