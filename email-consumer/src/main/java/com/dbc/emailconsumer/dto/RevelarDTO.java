package com.dbc.emailconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevelarDTO {
    private String nomeTreinador;
    private String destinatario;
    private String mensagem;
    private PokeDadosDTO pokeDadosDTO;
}
