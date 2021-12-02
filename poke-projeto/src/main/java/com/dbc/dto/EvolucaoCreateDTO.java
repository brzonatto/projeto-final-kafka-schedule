package com.dbc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvolucaoCreateDTO {
    @NotNull
    @ApiModelProperty("Estágio básico do Pokémon")
    private Integer idEstagioUm;

    @NotNull
    @ApiModelProperty("Primeira Evolução")
    private Integer idEstagioDois;

    @ApiModelProperty("Segunda Evolução")
    private Integer idEstagioTres;
}
