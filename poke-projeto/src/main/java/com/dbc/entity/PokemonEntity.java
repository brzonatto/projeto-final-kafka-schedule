package com.dbc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity(name = "pokemon")
public class PokemonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_pokemon")
    @SequenceGenerator(name = "seq_id_pokemon", sequenceName = "seq_id_pokemon", allocationSize = 1)
    @Column(name = "id_pokemon")
    private Integer idPokemon;

    @Column(name = "numero_pokemon")
    private Integer numero;

    @Column(name = "nome_pokemon")
    private String nome;

    @Column(name = "level_pokemon")
    private Integer level;

    @Column(name = "peso_pokemon")
    private Double peso;

    @Column(name = "altura_pokemon")
    private Double altura;

    @Column(name = "categoria_pokemon")
    private String categoria;

    @Column(name = "regiao_dominante_poke_lendario")
    private String regiaoDominante;

    @Embedded
    private StatusEntity status;

    @JsonIgnore
    @OneToMany(mappedBy = "pokemon", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<TipoPokemonEntity> tipos;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "pokemon_habilidade",
            joinColumns = @JoinColumn(name = "fk_pokemon_id_pokemon"),
            inverseJoinColumns = @JoinColumn(name = "fk_habilidade_id_habilidade")
    )
    private Set<HabilidadeEntity> habilidades;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_id_evolucao", referencedColumnName = "id_evolucao")
    private EvolucaoEntity evolucaoEntity;
}
