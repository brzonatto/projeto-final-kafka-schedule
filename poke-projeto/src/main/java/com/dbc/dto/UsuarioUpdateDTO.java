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
public class UsuarioUpdateDTO {
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Nova senha do usuário")
    private String newSenha;

    @NotEmpty
    @NotBlank
    @ApiModelProperty("Antiga senha do usuário")
    private String oldSenha;
}
