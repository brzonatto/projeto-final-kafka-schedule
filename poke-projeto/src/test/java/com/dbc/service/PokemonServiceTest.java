package com.dbc.service;

import com.dbc.dto.PokemonCreateDTO;
import com.dbc.dto.PokemonDTO;
import com.dbc.dto.StatusDTO;
import com.dbc.entity.PokemonEntity;
import com.dbc.entity.StatusEntity;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.kafka.Producer;
import com.dbc.repository.HabilidadeRepository;
import com.dbc.repository.PokemonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;
    @Mock
    private HabilidadeRepository habilidadeRepository;
    @Mock
    private EvolucaoService evolucaoService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private PokeDadosService pokeDadosService;
    @Mock
    private Producer producer;

    @InjectMocks
    private PokemonService pokemonService;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSerLendario() {
        PokemonCreateDTO pokemonCreateDTO = new PokemonCreateDTO();
        StatusDTO statusDTO = new StatusDTO(100, 100, 100, 100, 100, 80);
        pokemonCreateDTO.setStatus(statusDTO);
        Integer soma = pokemonService.somaStatus(pokemonCreateDTO);

        Assertions.assertEquals(soma, 580);
    }

    @Test
    public void deletePokemon() throws RegraDeNegocioException, JsonProcessingException {
        PokemonEntity pokemon = new PokemonEntity();
        doReturn(Optional.of(pokemon)).when(pokemonRepository).findById(1);
        pokemonService.delete(1);
        verify(pokemonRepository, times(1)).delete(pokemon);
    }

    @Test
    public void createPokemon() throws RegraDeNegocioException {
        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setStatus(new StatusEntity(10, 10, 10, 10, 10, 10));
        PokemonDTO pokemonDTO = new PokemonDTO();
        PokemonCreateDTO pokemonCreateDTO = new PokemonCreateDTO();
        pokemonCreateDTO.setStatus(new StatusDTO(10, 10, 10, 10, 10, 10));

        when(objectMapper.convertValue(pokemonCreateDTO, PokemonEntity.class)).thenReturn(pokemon);
        when(pokemonRepository.save(pokemon)).thenReturn(pokemon);
        when(objectMapper.convertValue(pokemon, PokemonDTO.class)).thenReturn(pokemonDTO);

        pokemonService.create(pokemonCreateDTO);

        verify(pokemonRepository, times(1)).save(pokemon);
    }


}