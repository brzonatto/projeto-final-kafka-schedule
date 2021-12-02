package com.dbc.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class StatusEntity implements Serializable {
    @Column(name = "hp_status")
    private Integer hp;

    @Column(name = "ataque_status")
    private Integer ataque;

    @Column(name = "defesa_status")
    private Integer defesa;

    @Column(name = "ataque_especial_status")
    private Integer especialAtaque;

    @Column(name = "defesa_especial_status")
    private Integer especialDefesa;

    @Column(name = "velocidade_status")
    private Integer velocidade;
}
