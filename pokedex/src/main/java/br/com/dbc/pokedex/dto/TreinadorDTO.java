package br.com.dbc.pokedex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreinadorDTO {
    private String idTreinador;
    private String nomeCompleto;
    private Date dataNascimento;
    private String sexo;
    private String email;
}
