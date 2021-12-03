package com.dbc.service;

import com.dbc.dto.PokeDadosDTO;
import com.dbc.dto.TipoPokemonCreateDTO;
import com.dbc.dto.TipoPokemonDTO;
import com.dbc.entity.PokemonEntity;
import com.dbc.entity.TipoPokemonEntity;
import com.dbc.enums.Tipo;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.kafka.Producer;
import com.dbc.repository.PokemonRepository;
import com.dbc.repository.TipoPokemonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoPokemonService {
    private final TipoPokemonRepository tipoPokemonRepository;
    private final PokemonRepository pokemonRepository;
    private final PokeDadosService pokeDadosService;
    private final ObjectMapper objectMapper;
    private final Producer producer;

    private TipoPokemonEntity findById(Integer id) throws RegraDeNegocioException {
        TipoPokemonEntity entity = tipoPokemonRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Tipo não encontrado"));
        return entity;
    }

    public Boolean existTipoNoPokemon(Integer idPokemon, Tipo tipo) {
        if (tipoPokemonRepository.searchByPokemon(idPokemon, tipo) == 0) {
            return false;
        }
        return true;
    }

    public TipoPokemonDTO create(Integer idPokemon, Tipo tipo) throws RegraDeNegocioException, JsonProcessingException {
        PokemonEntity entity = pokemonRepository.findById(idPokemon).orElseThrow(() -> new RegraDeNegocioException("NAO ACHOU O POKE"));
        if (existTipoNoPokemon(idPokemon, tipo)) {
            throw new RegraDeNegocioException("este tipo ja está cadastrado para este pokémon");
        }
        TipoPokemonEntity tipoPokemonEntity = new TipoPokemonEntity();
        tipoPokemonEntity.setPokemon(entity);
        tipoPokemonEntity.setTipo(tipo);
        TipoPokemonEntity tipoPokemonEntityCriado = tipoPokemonRepository.save(tipoPokemonEntity);
        TipoPokemonDTO tipoPokemonDTO = objectMapper.convertValue(tipoPokemonEntityCriado, TipoPokemonDTO.class);
        tipoPokemonDTO.setIdPokemon(idPokemon);
        PokeDadosDTO pokeDadosDTO = pokeDadosService.list(idPokemon).get(0);
        producer.sendUpdate(pokeDadosDTO);
        return tipoPokemonDTO;
    }

    public List<TipoPokemonDTO> list() {
        return tipoPokemonRepository.findAll().stream()
                .map(tipo -> {
                    TipoPokemonDTO tipoPokemonDTO = objectMapper.convertValue(tipo, TipoPokemonDTO.class);
                    tipoPokemonDTO.setIdPokemon(tipo.getPokemon().getIdPokemon());
                    return tipoPokemonDTO;
                })
                .collect(Collectors.toList());
    }

    public TipoPokemonDTO update(Integer idTipo, Tipo tipo) throws RegraDeNegocioException, JsonProcessingException {
        TipoPokemonEntity tipoPokemonEntity = findById(idTipo);
        if (existTipoNoPokemon(tipoPokemonEntity.getPokemon().getIdPokemon(), tipo)) {
            throw new RegraDeNegocioException("este tipo ja está cadastrado para este pokémon");
        }
        tipoPokemonEntity.setTipo(tipo);
        TipoPokemonEntity update = tipoPokemonRepository.save(tipoPokemonEntity);
        TipoPokemonDTO tipoPokemonDTO = objectMapper.convertValue(update, TipoPokemonDTO.class);
        tipoPokemonDTO.setIdPokemon(tipoPokemonEntity.getPokemon().getIdPokemon());
        PokeDadosDTO pokeDadosDTO = pokeDadosService.list(tipoPokemonEntity.getPokemon().getIdPokemon()).get(0);
        producer.sendUpdate(pokeDadosDTO);
        return tipoPokemonDTO;
    }

    public void delete(Integer idTipo) throws RegraDeNegocioException, JsonProcessingException {
        TipoPokemonEntity tipoPokemonEntity = findById(idTipo);
        tipoPokemonRepository.delete(tipoPokemonEntity);
        PokeDadosDTO pokeDadosDTO = pokeDadosService.list(tipoPokemonEntity.getPokemon().getIdPokemon()).get(0);
        producer.sendUpdate(pokeDadosDTO);
    }
}
