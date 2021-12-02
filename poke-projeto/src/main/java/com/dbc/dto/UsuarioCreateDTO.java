package com.dbc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Usuário")
    private String usuario;

    @NotEmpty
    @NotBlank
    @ApiModelProperty("Senha do usuário")
    private String senha;

    @NotNull
    @NotEmpty
    @ApiModelProperty("Grupos")
    private List<Integer> grupos;
}
