package com.dbc.controller;

import com.dbc.dto.*;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@Validated
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> list(@RequestParam(value = "loginUsuario" , required = false) String loginUsuario) throws RegraDeNegocioException {
        return usuarioService.list(loginUsuario);
    }

    @PostMapping
    public UsuarioDTO create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return usuarioService.create(usuarioCreateDTO);
    }

    @PutMapping("/{idUsuario}")
    public UsuarioDTO update(@PathVariable("idUsuario") Integer idUsuario,
                                   @RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return usuarioService.update(idUsuario, usuarioCreateDTO);
    }

    @DeleteMapping("/{idUsuario}")
    public void deleteById(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.deleteByid(idUsuario);
    }

    @PutMapping("/update-password/{loginUsuario}")
    public UsuarioDTO updatePassword(@PathVariable("loginUsuario") String loginUsuario,
                           @RequestBody @Valid UsuarioUpdateDTO usuarioUpdateDTO)
            throws RegraDeNegocioException {
        return usuarioService.updatePassword(loginUsuario, usuarioUpdateDTO);
    }
}
