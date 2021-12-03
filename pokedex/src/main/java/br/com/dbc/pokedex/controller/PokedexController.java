package br.com.dbc.pokedex.controller;

import br.com.dbc.pokedex.dto.*;
import br.com.dbc.pokedex.exceptions.RegraDeNegocioException;
import br.com.dbc.pokedex.service.PokedexService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/pokedex")
@Validated
@RequiredArgsConstructor
@Slf4j
public class PokedexController {
    private final PokedexService pokedexService;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        return pokedexService.auth(loginDTO);
    }

    @ApiOperation(value = "Cria uma nova pokedex")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a pokedex criada com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @PostMapping("/create")
    public PokedexDTO create(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("idTreinador") String idTreinador) throws RegraDeNegocioException {
        return pokedexService.create(authorizationHeader, idTreinador);
    }

    @PutMapping
    public PokedexDTO revelarPokemon( @RequestParam("numeroPokemon") Integer numeroPokemon,
                                         @RequestParam("idTreinador") String idTreinador,
                                         @RequestHeader("Authorization") String authorizationHeader)
            throws RegraDeNegocioException {
        return pokedexService.revelarPokemon(numeroPokemon, idTreinador, authorizationHeader);
    }

    @GetMapping("/dados")
    public PokedexDadosDTO getDadosPokedex(@RequestParam String idTreinador) throws RegraDeNegocioException {
        return pokedexService.getDadosPokedex(idTreinador);
    }
}
