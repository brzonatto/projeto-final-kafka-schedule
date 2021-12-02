package br.com.dbc.pokedex.controller;

import br.com.dbc.pokedex.dto.TreinadorCreateDTO;
import br.com.dbc.pokedex.dto.TreinadorDTO;
import br.com.dbc.pokedex.exceptions.RegraDeNegocioException;
import br.com.dbc.pokedex.service.TreinadorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/treinador")
@Validated
@RequiredArgsConstructor
public class TreinadorController {
    private final TreinadorService treinadorService;

    @ApiOperation(value = "Cria um treinador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o treinador criado com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @PostMapping
    public TreinadorDTO create(@RequestBody @Valid TreinadorCreateDTO treinadorCreateDTO) {
        TreinadorDTO treinadorDTO = treinadorService.create(treinadorCreateDTO);
        return treinadorDTO;
    }

    @ApiOperation(value = "Retorna uma lista de treinadores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @GetMapping
    public List<TreinadorDTO> list() {
        return treinadorService.list();
    }

    @ApiOperation(value = "Edita um treinador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o treinador editado com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @PutMapping("/{idTreinador}")
    public TreinadorDTO update(@PathVariable("idTreinador") String idTreinador,
                               @RequestBody @Valid TreinadorCreateDTO treinadorCreateDTO) throws RegraDeNegocioException {
        TreinadorDTO treinadorDTO = treinadorService.update(idTreinador, treinadorCreateDTO);
        return treinadorDTO;
    }

    @ApiOperation(value = "Delete um treinador pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o treinador deletado com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @DeleteMapping("/{idTreinador}")
    public void delete(@PathVariable("idTreinador") String idTreinador) throws RegraDeNegocioException {
        treinadorService.delete(idTreinador);
    }
}
