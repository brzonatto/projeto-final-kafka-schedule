package br.com.dbc.pokedex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumoFinalDTO {
    private LocalDate dataResumo;
    private String email;
    private Integer totalTreinadores;
    private Integer totalPokemons;
    private Integer totalPokemonsRevelados;
    private Integer totalPokemonsReveladosHoje;
    private List<NumeroNomeDTO> pokemonsReveladosHoje;
}
