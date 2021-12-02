package br.com.dbc.pokedex.entity;

import br.com.dbc.pokedex.dto.PokeDadosDTO;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pokedex")
public class PokedexEntity {
    @Id
    private String idPokedex;
    private Integer quantidadeDePokemonsExistentes;
    private Integer quantidadePokemonsRevelados;
    private List<PokeDadosDTO> pokemons;
}
