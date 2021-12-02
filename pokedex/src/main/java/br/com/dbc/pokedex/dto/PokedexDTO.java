package br.com.dbc.pokedex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokedexDTO {
    private String idPokedex;
    private Integer quantidadeDePokemonsExistentes;
    private Integer quantidadePokemonsRevelados;
    private List<PokeDadosDTO> pokemons;
}
