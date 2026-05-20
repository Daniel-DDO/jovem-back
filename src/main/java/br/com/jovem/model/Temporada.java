package br.com.jovem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "temporadas")
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @NotNull
    @Column(nullable = false)
    private OffsetDateTime dataInicio;

    @NotNull
    @Column(nullable = false)
    private OffsetDateTime dataFim;

    @Column(nullable = false)
    private boolean ativa = true;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime criadoEm = OffsetDateTime.now();

}