package com.dbc.service;

import com.dbc.dto.PokemonCreateDTO;
import com.dbc.dto.StatusDTO;
import com.dbc.kafka.Producer;
import com.dbc.repository.HabilidadeRepository;
import com.dbc.repository.PokemonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PokemonServiceTest {

    @InjectMocks
    private PokemonService pokemonService;

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

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.pokemonService = new PokemonService(pokemonRepository, habilidadeRepository, evolucaoService, objectMapper, pokeDadosService, producer);
    }

    @Test
    void deveSerLendario() {
        PokemonCreateDTO pokemonCreateDTO = new PokemonCreateDTO();
        StatusDTO statusDTO = new StatusDTO(100, 100, 100, 100, 100, 80);
        pokemonCreateDTO.setStatus(statusDTO);
        Integer soma = pokemonService.somaStatus(pokemonCreateDTO);

        Assertions.assertEquals(soma, 600);
    }
}