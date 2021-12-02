package com.dbc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvolucaoNomesDTO {
    @ApiModelProperty("Nome do estágio básico")
    private String estagioUm;

    @ApiModelProperty("Nome da 1ª Evolução")
    private String estagioDois;

    @ApiModelProperty("Nome da 2ª Evolução")
    private String estagioTres;
}
