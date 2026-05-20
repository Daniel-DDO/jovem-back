package br.com.jovem.model;

import br.com.jovem.enums.TipoDestinoPontuacao;
import br.com.jovem.enums.TipoPontuacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "eventos_pontuacao")
public class EventoPontuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temporada_id", nullable = false)
    private Temporada temporada;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false, length = 20)
    private TipoDestinoPontuacao destino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tribo_temporada_id")
    private TriboTemporada triboTemporada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nacao_temporada_id")
    private NacaoTemporada nacaoTemporada;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false, length = 30)
    private TipoPontuacao tipo;

    @NotNull
    @Column(nullable = false)
    private Integer pontos;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id")
    private Missao missao;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime criadoEm = OffsetDateTime.now(ZoneOffset.UTC);

}