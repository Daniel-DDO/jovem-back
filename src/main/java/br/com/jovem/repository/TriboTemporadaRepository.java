package br.com.jovem.repository;

import br.com.jovem.model.TriboTemporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TriboTemporadaRepository extends JpaRepository<TriboTemporada, UUID> {
}