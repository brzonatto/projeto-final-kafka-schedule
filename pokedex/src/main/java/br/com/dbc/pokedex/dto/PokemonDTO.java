package br.com.dbc.pokedex.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("Número do Pokémon na pokédex")
    private Integer numero;

    @NotEmpty
    @NotBlank
    @ApiModelProperty("Nome do Pokémon")
    private String nome;

    @NotNull
    @Min(1)
    @ApiModelProperty("Level do Pokémon")
    private Integer level;

    @NotNull
    @ApiModelProperty("Peso do Pokémon")
    private Double peso;

    @NotNull
    @ApiModelProperty("Altura do Pokémon")
    private Double altura;

    @NotEmpty
    @NotBlank
    @ApiModelProperty("Categoria do Pokémon")
    private String categoria;

    @ApiModelProperty("Região dominante do Pokémon, caso lendário")
    private String regiaoDominante;

    @ApiModelProperty("Status do Pokémon")
    @NotNull
    private StatusDTO status;
}
