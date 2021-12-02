package com.dbc.controller;

import com.dbc.dto.EvolucaoCreateDTO;
import com.dbc.dto.EvolucaoDTO;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.service.EvolucaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/evolucao")
@Validated
@RequiredArgsConstructor
@Slf4j
public class EvolucaoController {
    private final EvolucaoService evolucaoService;

    @ApiOperation("Criar Evolução")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Evolução criada com sucesso!"),
            @ApiResponse(code = 400, message = "Evolução com dados inconsistentes"),
            @ApiResponse(code = 500, message = "Excessão no sistema")
    })
    public EvolucaoDTO create(@RequestBody @Valid EvolucaoCreateDTO evolucaoCreateDTO) throws RegraDeNegocioException {
        EvolucaoDTO evolucaoDTOCriado = evolucaoService.create(evolucaoCreateDTO);
        return evolucaoDTOCriado;
    }

    @ApiOperation("Listar Evoluções")
    @GetMapping
    public List<EvolucaoDTO> list() {
        return evolucaoService.list();
    }

    @ApiOperation("Editar Evolução")
    @PutMapping("/{idEvolucao}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Evolução editada com sucesso!"),
            @ApiResponse(code = 400, message = "Evolução não encontrada"),
            @ApiResponse(code = 500, message = "Excessão no sistema")
    })
    public EvolucaoDTO update(@PathVariable("idEvolucao") Integer idEvolucao, @RequestBody @Valid EvolucaoCreateDTO evolucaoCreateDTO) throws RegraDeNegocioException {
        return evolucaoService.update(idEvolucao, evolucaoCreateDTO);
    }

    @ApiOperation("Excluir Evolução")
    @DeleteMapping("/{idEvolucao}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Evolução excluída com sucesso!"),
            @ApiResponse(code = 400, message = "Evolução não encontrada"),
            @ApiResponse(code = 500, message = "Excessão no sistema")
    })
    public void delete(@PathVariable("idEvolucao") Integer idEvolucao) throws RegraDeNegocioException {
        evolucaoService.delete(idEvolucao);
    }

}
