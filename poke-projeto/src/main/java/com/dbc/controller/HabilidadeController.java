package com.dbc.controller;

import com.dbc.dto.HabilidadeCreateDTO;
import com.dbc.dto.HabilidadeDTO;
import com.dbc.dto.PokemonHabilidadeCreateDTO;
import com.dbc.dto.PokemonHabilidadeDTO;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.service.HabilidadeService;
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
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/habilidade")
public class HabilidadeController {

    private final HabilidadeService habilidadeService;

    @ApiOperation("Adicionar Habilidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Habilidade criada com sucesso!"),
            @ApiResponse(code = 400, message = "Habilidade com dados inconsistentes"),
            @ApiResponse(code = 500, message = "Exceção no sistema")
    })
    @PostMapping
    public HabilidadeDTO create(@RequestBody @Valid HabilidadeCreateDTO habilidadeCreateDTO) throws RegraDeNegocioException {
        HabilidadeDTO habilidadeCriada = habilidadeService.create(habilidadeCreateDTO);
        return habilidadeCriada;
    }

    @ApiOperation("Listar Habilidade")
    @GetMapping
    public List<HabilidadeDTO> list() {
        return habilidadeService.list();
    }


    @ApiOperation("Editar Habilidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Habilidade editada com sucesso!"),
            @ApiResponse(code = 400, message = "Habilidade não encontrada"),
            @ApiResponse(code = 500, message = "Exceção no sistema")
    })
    @PutMapping("/{idHabilidade}")
    public HabilidadeDTO update(@PathVariable("idHabilidade") Integer idHabilidade,
                                @Valid @RequestBody HabilidadeCreateDTO habilidadeCreateDTO) throws RegraDeNegocioException {
        HabilidadeDTO habilidadeEditada = habilidadeService.update(idHabilidade, habilidadeCreateDTO);
        return habilidadeEditada;
    }


    @ApiOperation("Excluir Habilidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Habilidade excluída com sucesso!"),
            @ApiResponse(code = 400, message = "Habilidade não encontrada"),
            @ApiResponse(code = 500, message = "Exceção no sistema")
    })

    @DeleteMapping("/{idHabilidade}")
    public void delete(@PathVariable("idHabilidade") Integer idHabilidade) throws RegraDeNegocioException {
        habilidadeService.delete(idHabilidade);
    }
}
