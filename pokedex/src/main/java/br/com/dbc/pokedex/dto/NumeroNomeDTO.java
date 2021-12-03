package br.com.dbc.pokedex.dto;

import br.com.dbc.pokedex.entity.TreinadorEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumeroNomeDTO {
    private Integer numero;
    private String nome;
    private TreinadorEntity treinadorEntity;
}
