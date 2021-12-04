package com.dbc.emailconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokeDadosDTO {
    private PokemonDTO pokemon;

    private List<String> tipos;

    private List<String> habilidades;

    private EvolucaoNomesDTO evolucao;
}
