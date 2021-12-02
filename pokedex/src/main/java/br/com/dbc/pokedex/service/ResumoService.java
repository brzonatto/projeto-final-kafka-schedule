package br.com.dbc.pokedex.service;

import br.com.dbc.pokedex.dto.NumeroNomeDTO;
import br.com.dbc.pokedex.dto.ResumoDTO;
import br.com.dbc.pokedex.entity.ResumoEntity;
import br.com.dbc.pokedex.exceptions.RegraDeNegocioException;
import br.com.dbc.pokedex.kafka.Producer;
import br.com.dbc.pokedex.repository.ResumoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumoService {
    private final ResumoRepository resumoRepository;
    private final Producer producer;
    private final ObjectMapper objectMapper;


    public ResumoEntity salvarResumoPokedex() {
        ResumoDTO resumoDTO = new ResumoDTO();
        ResumoEntity resumo = objectMapper.convertValue(resumoDTO, ResumoEntity.class);
        return resumoRepository.save(resumo);
    }

    public void enviarResumoPokedex() {
    }

//    public void enviarResumoPokedex() {
//        resumoRepository.save();
////        ResumoEntity resumo = resumoRepository.findById(id);
////        List<ResumoEntity> resumos = resumoRepository.findAll();
////        for (ResumoEntity key: resumos) {
////            producer.sendMessage(new ResumoDTO(key.getDataResumo(), key.getTotalTreinadores(), key.getTotalPokemons(), reveladosHoje()));
//    }
}
