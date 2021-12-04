package com.dbc.emailconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {
    private Integer hp;

    private Integer ataque;

    private Integer defesa;

    private Integer especialAtaque;

    private Integer especialDefesa;

    private Integer velocidade;
}
