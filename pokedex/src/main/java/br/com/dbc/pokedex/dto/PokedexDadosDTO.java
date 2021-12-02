package br.com.dbc.pokedex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokedexDadosDTO {
    private TreinadorDTO treinador;
    private PokedexDTO pokedex;
}
