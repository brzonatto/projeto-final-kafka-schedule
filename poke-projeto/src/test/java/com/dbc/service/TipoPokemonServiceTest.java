package com.dbc.service;

import com.dbc.dto.PokemonCreateDTO;
import com.dbc.dto.TipoPokemonCreateDTO;
import com.dbc.enums.Tipo;
import com.dbc.kafka.Producer;
import com.dbc.repository.PokemonRepository;
import com.dbc.repository.TipoPokemonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TipoPokemonServiceTest {

    @InjectMocks
    private TipoPokemonService tipoPokemonService;

    @Mock
    private TipoPokemonRepository tipoPokemonRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private PokeDadosService pokeDadosService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Producer producer;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.tipoPokemonService = new TipoPokemonService(tipoPokemonRepository, pokemonRepository, pokeDadosService, objectMapper, producer);
    }

    @Test
    void deveSerTipoCorreto(){
        TipoPokemonCreateDTO tipoPokemonCreateDTO = new TipoPokemonCreateDTO();
        tipoPokemonCreateDTO.setTipo(Tipo.TERRA);

        Assertions.assertEquals(tipoPokemonCreateDTO.getTipo(), Tipo.TERRA);
    }

}