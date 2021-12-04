package com.dbc.emailconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDTO {
    @NotNull
    @Min(1)
    private Integer numero;

    @NotEmpty
    @NotBlank
    private String nome;

    @NotNull
    @Min(1)
    private Integer level;

    @NotNull
    private Double peso;

    @NotNull
    private Double altura;

    @NotEmpty
    @NotBlank
    private String categoria;

    private String regiaoDominante;

    @NotNull
    private StatusDTO status;
}
