package com.dbc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity(name = "habilidade")
public class HabilidadeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_habilidade")
    @SequenceGenerator(name = "seq_id_habilidade", sequenceName = "seq_id_habilidade", allocationSize = 1)
    @Column(name = "id_habilidade")
    private Integer idHabilidade;

    @Column(name = "nome_habilidade")
    private String nome;

    @Column(name = "mult_de_poder_habilidade")
    private Double multiplicacaoDePoder;

    @JsonIgnore
    @ManyToMany(mappedBy = "habilidades")
    private Set<PokemonEntity> pokemons;
}
