package br.com.jovem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "nacoes")
public class Nacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    private String logoUrl;

    @Column(length = 20)
    private String cor;

    @Column(nullable = false)
    private boolean ativa = true;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime criadoEm = OffsetDateTime.now();

}