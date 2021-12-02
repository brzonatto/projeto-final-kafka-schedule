package br.com.dbc.pokedex.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokeDadosDTO {
    @ApiModelProperty("Pokémon")
    private PokemonDTO pokemon;

    @ApiModelProperty("Tipos")
    private List<String> tipos;

    @ApiModelProperty("Habilidades")
    private List<String> habilidades;

    @ApiModelProperty("Evolução")
    private EvolucaoNomesDTO evolucao;
}
