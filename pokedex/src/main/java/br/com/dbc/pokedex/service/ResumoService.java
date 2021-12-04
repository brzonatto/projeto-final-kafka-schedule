package br.com.dbc.pokedex.service;

import br.com.dbc.pokedex.dto.ResumoFinalDTO;
import br.com.dbc.pokedex.entity.PokedexEntity;
import br.com.dbc.pokedex.entity.ResumoEntity;
import br.com.dbc.pokedex.entity.TreinadorEntity;
import br.com.dbc.pokedex.kafka.Producer;
import br.com.dbc.pokedex.repository.ResumoRepository;
import br.com.dbc.pokedex.repository.TreinadorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumoService {
    private final ResumoRepository resumoRepository;
    private final Producer producer;
    private final ObjectMapper objectMapper;
    private final TreinadorRepository treinadorRepository;


    public void salvarResumoPokedex() {
        List<TreinadorEntity> treinadores = treinadorRepository.findAll();
        for (TreinadorEntity key :treinadores) {
            if (resumoRepository.existsByTreinador(key)) {
                ResumoEntity resumoExistente = resumoRepository.searchResumoByTreinador(new ObjectId(key.getIdTreinador()));
                resumoExistente.setDataResumo(LocalDateTime.now());
                resumoExistente.setPokedex(key.getPokedexEntity());
                resumoExistente.setTreinador(key);
                resumoExistente.setTotalTreinadores(treinadores.size());
                resumoRepository.save(resumoExistente);
            } else {
                PokedexEntity pokedex = key.getPokedexEntity();
                ResumoEntity resumo = new ResumoEntity();
                resumo.setDataResumo(LocalDateTime.now());
                resumo.setPokedex(pokedex);
                resumo.setTreinador(key);
                resumo.setTotalTreinadores(treinadores.size());
                resumoRepository.save(resumo);
            }
        }
    }

    public void enviarResumoPokedex() {

    }


}
