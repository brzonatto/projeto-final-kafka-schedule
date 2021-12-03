package com.dbc.controller;

import com.dbc.dto.PokemonCreateDTO;
import com.dbc.dto.PokemonDTO;
import com.dbc.dto.PokemonHabilidadeCreateDTO;
import com.dbc.dto.PokemonHabilidadeDTO;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.service.PokemonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/pokemon")
public class PokemonController {
    private final PokemonService pokemonService;

    @ApiOperation("Adicionar Pokémon")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pokémon criado com sucesso!"),
            @ApiResponse(code = 400, message = "Pokémon com dados inconsistentes"),
            @ApiResponse(code = 500, message = "Exceção no sistema")
    })
    @PostMapping
    public PokemonDTO create(@RequestBody @Valid PokemonCreateDTO pokemonCreateDTO) throws RegraDeNegocioException {
        PokemonDTO pokemonCriado = pokemonService.create(pokemonCreateDTO);
        return pokemonCriado;
    }

    @ApiOperation("Listar Pokémon")
    @GetMapping
    public List<PokemonDTO> list(){return pokemonService.list();}


    @ApiOperation("Editar Pokémon")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pokémon editado com sucesso!"),
            @ApiResponse(code = 400, message = "Pokémon não encontrado"),
            @ApiResponse(code = 500, message = "Exceção no sistema")
    })
    @PutMapping("/{idPokemon}")
    public PokemonDTO update(@PathVariable("idPokemon") Integer id,
                             @RequestBody @Valid PokemonCreateDTO pokemonCreateDTO) throws RegraDeNegocioException, JsonProcessingException {
        return pokemonService.update(id, pokemonCreateDTO);
    }

    @ApiOperation("Excluir Pokémon")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pokémon excluído com sucesso!"),
            @ApiResponse(code = 400, message = "Pokémon não encontrado"),
            @ApiResponse(code = 500, message = "Exceção no sistema")
    })
    @DeleteMapping("/{idPokemon}")
    public void delete(@PathVariable("idPokemon") Integer id) throws Exception {
        pokemonService.delete(id);
    }


   @PostMapping("/{idPokemon}/add-habilidade")
    public PokemonHabilidadeDTO addHabilidade(@PathVariable("idPokemon") Integer idPokemon, @RequestBody @Valid PokemonHabilidadeCreateDTO pokemonHabilidadeCreateDTO) throws RegraDeNegocioException, JsonProcessingException {
        return pokemonService.setHabilidades(idPokemon, pokemonHabilidadeCreateDTO);
   }
}
