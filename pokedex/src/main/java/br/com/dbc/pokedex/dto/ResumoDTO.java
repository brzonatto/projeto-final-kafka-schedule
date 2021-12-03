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
public class ResumoDTO {
    private LocalDate dataResumo;
    private Integer totalTreinadores;
    private Integer totalPokemons;
    private List<NumeroNomeDTO> pokemonsReveladosHoje;
}
