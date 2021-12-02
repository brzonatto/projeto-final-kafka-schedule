package com.dbc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDTO {
    @NotBlank
    @NotEmpty
    @ApiModelProperty("Nome do grupo")
    private String nome;

    @NotBlank
    @NotEmpty
    @ApiModelProperty("Descrição do grupo")
    private String descricao;
}
