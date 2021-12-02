package com.dbc.controller;

import com.dbc.dto.TipoPokemonCreateDTO;
import com.dbc.dto.TipoPokemonDTO;
import com.dbc.enums.Tipo;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.service.TipoPokemonService;
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
@RequestMapping("/tipo")
@Validated
@RequiredArgsConstructor
@Slf4j
public class TipoPokemonController {
    private final TipoPokemonService tipoPokemonService;

    @ApiOperation("Adicionar tipo ao Pokémon")
    @PostMapping("/{idPokemon}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tipo criado com sucesso!"),
            @ApiResponse(code = 400, message = "Tipo com dados inconsistentes"),
            @ApiResponse(code = 500, message = "Excessão no sistema")
    })
    public TipoPokemonDTO create(@PathVariable("idPokemon") Integer idPokemon, @RequestParam @Valid Tipo tipo) throws RegraDeNegocioException {
        TipoPokemonDTO tipoPokemonDTOCriado = tipoPokemonService.create(idPokemon, tipo);
        return tipoPokemonDTOCriado;
    }

    @ApiOperation("Listar os tipos do Pokémon")
    @GetMapping
    public List<TipoPokemonDTO> list() {
        return tipoPokemonService.list();
    }

    @ApiOperation("Editar tipos do Pokémon")
    @PutMapping("/{idTipo}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tipo editado com sucesso!"),
            @ApiResponse(code = 400, message = "Tipo não encontrado"),
            @ApiResponse(code = 500, message = "Excessão no sistema")
    })
    public TipoPokemonDTO update(@PathVariable("idTipo") Integer idTipo, @RequestParam @NotNull Tipo tipo) throws RegraDeNegocioException {
        return tipoPokemonService.update(idTipo, tipo);
    }

    @ApiOperation("Excluir tipo do Pokémon")
    @DeleteMapping("/{idTipo}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tipo excluído com sucesso!"),
            @ApiResponse(code = 400, message = "Tipo não encontrado"),
            @ApiResponse(code = 500, message = "Excessão no sistema")
    })
    public void delete(@PathVariable("idTipo") Integer idTipo) throws RegraDeNegocioException {
        tipoPokemonService.delete(idTipo);
    }
}
