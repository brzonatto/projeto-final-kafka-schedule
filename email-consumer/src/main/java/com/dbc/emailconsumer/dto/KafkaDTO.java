package com.dbc.emailconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaDTO {
    private String nome;
    private String destinatario;
    private String assunto;
    private String texto;
}
