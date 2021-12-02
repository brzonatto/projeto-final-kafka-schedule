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
public class UsuarioDTO {
    @NotNull
    @ApiModelProperty("ID do usuário")
    private Integer idUsuario;

    @NotEmpty
    @NotBlank
    @ApiModelProperty("Usuário")
    private String usuario;

    @NotNull
    @ApiModelProperty("Lista de grupos")
    private List<GrupoDTO> grupos;
}
