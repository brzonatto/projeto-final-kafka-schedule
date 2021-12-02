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
public class PokemonHabilidadeCreateDTO {
    @NotNull
    @ApiModelProperty("ID de Habilidades")
    private List<Integer> idHabilidades;
}
