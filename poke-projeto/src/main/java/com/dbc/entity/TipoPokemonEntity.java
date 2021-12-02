package com.dbc.entity;

import com.dbc.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity(name = "pokemon_tipo")
public class TipoPokemonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_pokemon_tipo")
    @SequenceGenerator(name = "seq_id_pokemon_tipo", sequenceName = "seq_id_pokemon_tipo", allocationSize = 1)
    @Column(name = "id_pokemon_tipo")
    private Integer idTipoPokemon;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_pokemon_id_pokemon", referencedColumnName = "id_pokemon")
    private PokemonEntity pokemon;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome_tipo")
    private Tipo tipo;
}
