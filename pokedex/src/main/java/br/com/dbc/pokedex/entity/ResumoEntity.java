package br.com.dbc.pokedex.entity;

import br.com.dbc.pokedex.dto.NumeroNomeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "resumo")
public class ResumoEntity {

    @Id
    private Integer idResumo;
    private LocalDateTime dataResumo;
    private Integer totalTreinadores;
    private Integer totalPokemons;
    @DBRef
    private TreinadorEntity treinadorEntity;
}
