package com.dbc;

import com.dbc.dto.PokemonCreateDTO;
import com.dbc.service.PokemonService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PokeTests {

    @Test
    public void deveriaRetornarNomeCorreto(){
        PokemonCreateDTO poke = new PokemonCreateDTO();

        poke.setNome("Charmander");

        Assert.assertEquals("Charmander", poke.getNome());
    }

    @Test
    public void deveriaRetornarCategoriaCorreta(){
        PokemonCreateDTO poke = new PokemonCreateDTO();

        poke.setCategoria("PLANTA");

        Assert.assertEquals("PLANTA", poke.getCategoria());
    }

    @Test
    public void deveriaRetornarStatusCorreto(){
        PokemonCreateDTO poke = new PokemonCreateDTO();

        poke.setPeso(110.50);

        Assert.assertFalse(poke.getPeso() == null);
    }


}