package com.dbc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {
    @ApiModelProperty("Hp do Pokémon")
    private Integer hp;

    @ApiModelProperty("Ataque do Pokémon")
    private Integer ataque;

    @ApiModelProperty("Defesa do Pokémon")
    private Integer defesa;

    @ApiModelProperty("Especial Ataque do Pokémon")
    private Integer especialAtaque;

    @ApiModelProperty("Especial Defesa do Pokémon")
    private Integer especialDefesa;

    @ApiModelProperty("Velocidade do Pokémon")
    private Integer velocidade;
}
