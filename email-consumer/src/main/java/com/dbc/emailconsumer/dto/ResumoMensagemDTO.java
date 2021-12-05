package com.dbc.emailconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumoMensagemDTO {
    private ResumoFinalDTO resumoFinalDTO;
    private String mensagem;
}
