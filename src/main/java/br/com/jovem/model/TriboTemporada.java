package br.com.jovem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "tribos_temporada",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tribo_id", "temporada_id"})
        }
)
public class TriboTemporada {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tribo_id", nullable = false)
    private Tribo tribo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temporada_id", nullable = false)
    private Temporada temporada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nacao_temporada_id")
    private NacaoTemporada nacaoTemporada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lider_id")
    private Pessoa lider;

    @Column(nullable = false)
    private Integer totalPontos = 0;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime criadoEm = OffsetDateTime.now();

}