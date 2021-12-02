package com.dbc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "evolucao")
public class EvolucaoEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_evolucao")
    @SequenceGenerator(name = "seq_id_evolucao", sequenceName = "seq_id_evolucao", allocationSize = 1)
    @Column(name = "id_evolucao")
    private Integer idEvolucao;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_poke_estagio_1_evolucao", referencedColumnName = "id_pokemon")
    private PokemonEntity estagioUm;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_poke_estagio_2_evolucao", referencedColumnName = "id_pokemon")
    private PokemonEntity estagioDois;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_poke_estagio_3_evolucao", referencedColumnName = "id_pokemon", nullable = true)
    private PokemonEntity estagioTres;
}
