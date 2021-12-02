package com.dbc.service;

import com.dbc.dto.*;
import com.dbc.entity.HabilidadeEntity;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.repository.HabilidadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HabilidadeService {
    private final HabilidadeRepository habilidadeRepository;
    private final ObjectMapper objectMapper;

    private HabilidadeEntity findById(Integer id) throws RegraDeNegocioException {
        HabilidadeEntity entity = habilidadeRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Não encontrado"));
        return entity;
    }

    public HabilidadeDTO create(HabilidadeCreateDTO habilidadeCreateDTO) {
        HabilidadeEntity habilidadeEntity = objectMapper.convertValue(habilidadeCreateDTO, HabilidadeEntity.class);
        HabilidadeEntity habilidadeCriada = habilidadeRepository.save(habilidadeEntity);
        return objectMapper.convertValue(habilidadeCriada, HabilidadeDTO.class);
    }

    public List<HabilidadeDTO> list() {
        return habilidadeRepository.findAll().stream()
                .map(habilidade -> objectMapper.convertValue(habilidade, HabilidadeDTO.class))
                .collect(Collectors.toList());
    }

    public HabilidadeDTO update(Integer idHabilidade, HabilidadeCreateDTO habilidadeCreateDTO) throws RegraDeNegocioException {
        findById(idHabilidade);
        HabilidadeEntity entity = objectMapper.convertValue(habilidadeCreateDTO, HabilidadeEntity.class);
        entity.setIdHabilidade(idHabilidade);
        HabilidadeEntity update = habilidadeRepository.save(entity);
        HabilidadeDTO habilidadeDTO = objectMapper.convertValue(update, HabilidadeDTO.class);
        return habilidadeDTO;
    }

    public void delete(Integer idHabilidade) throws RegraDeNegocioException{
        HabilidadeEntity habilidadeEntity = findById(idHabilidade);
        habilidadeRepository.deleteAllByIdHabilidade(idHabilidade);
        habilidadeRepository.delete(habilidadeEntity);
    }
}
