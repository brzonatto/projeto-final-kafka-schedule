package com.dbc.controller;

import com.dbc.dto.PokeDadosDTO;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.service.PokeDadosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/pokemon/dados")
public class PokeDadosController {
    private final PokeDadosService pokeDadosService;

    @GetMapping
    public List<PokeDadosDTO> list(@RequestParam(value = "idPoke",required = false) Integer idPoke) throws RegraDeNegocioException {
        return pokeDadosService.list(idPoke);
    }
}
