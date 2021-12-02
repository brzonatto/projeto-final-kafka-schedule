package com.dbc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonHabilidadeDTO {
    @NotNull
    @ApiModelProperty("Pokémon")
    private PokemonDTO pokemon;

    @NotNull
    @ApiModelProperty("Lista de Habilidades")
    private List<HabilidadeDTO> habilidades;
}
