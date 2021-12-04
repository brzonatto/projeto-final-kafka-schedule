package br.com.dbc.pokedex.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "resumo")
public class ResumoEntity {
    @Id
    private String idResumo;
    private LocalDateTime dataResumo;
    private Integer totalTreinadores;
    private TreinadorEntity treinador;
    private PokedexEntity pokedex;
}
