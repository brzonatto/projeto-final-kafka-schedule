package com.dbc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvolucaoDTO {
    @ApiModelProperty("ID da evolução")
    private Integer idEvolucao;

    @ApiModelProperty("Estágio básico Pokémon")
    @NotNull
    private PokemonDTO estagioUm;

    @ApiModelProperty("Primeira Evolução")
    @NotNull
    private PokemonDTO estagioDois;

    @ApiModelProperty("Segunda Evolução")
    private PokemonDTO estagioTres;
}
