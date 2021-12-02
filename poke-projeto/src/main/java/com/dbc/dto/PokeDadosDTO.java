package com.dbc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokeDadosDTO {
    @ApiModelProperty("Pokémon")
    private PokemonCreateDTO pokemon;

    @ApiModelProperty("Tipos")
    private List<TipoPokemonCreateDTO> tipos;

    @ApiModelProperty("Habilidades")
    private List<HabilidadeCreateDTO> habilidades;

    @ApiModelProperty("Evolução")
    private EvolucaoNomesDTO evolucao;
}
