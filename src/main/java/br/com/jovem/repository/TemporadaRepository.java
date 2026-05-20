package br.com.jovem.repository;

import br.com.jovem.model.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, UUID> {
}