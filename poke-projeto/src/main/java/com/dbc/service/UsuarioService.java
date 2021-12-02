package com.dbc.service;

import com.dbc.dto.*;
import com.dbc.entity.*;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.repository.GrupoRepository;
import com.dbc.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final GrupoRepository grupoRepository;
    private final ObjectMapper objectMapper;

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Não encontrado"));
    }

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        List<GrupoEntity> grupoEntities = new ArrayList<>();
        for (Integer key : usuarioCreateDTO.getGrupos()) {
            grupoEntities.add(grupoRepository.findById(key)
                    .orElseThrow(() -> new RegraDeNegocioException("Grupo não encontrado")));
        }
        UsuarioEntity entity = new UsuarioEntity();
        entity.setLogin(usuarioCreateDTO.getUsuario());
        entity.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));
        entity.setGrupos(grupoEntities);
        UsuarioEntity save = usuarioRepository.save(entity);
        return new UsuarioDTO(
                save.getIdUsuario(),
                save.getUsername(),
                save.getGrupos()
                        .stream()
                        .map(grupo -> objectMapper.convertValue(grupo, GrupoDTO.class))
                        .collect(Collectors.toList()));
    }

    public List<UsuarioDTO> list(String loginUsuario) throws RegraDeNegocioException {
        if (loginUsuario == null) {
            return usuarioRepository.findAll()
                    .stream()
                    .map(this::setUsuarioDTO)
                    .collect(Collectors.toList());
        }
        List<UsuarioDTO> usuarios = new ArrayList<>();
        UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(loginUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
        usuarios.add(setUsuarioDTO(usuarioEntity));
        return usuarios;
    }

    public UsuarioDTO setUsuarioDTO(UsuarioEntity usuarioEntity) {
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        usuarioDTO.setUsuario(usuarioEntity.getLogin());
        usuarioDTO.setGrupos(
                usuarioEntity.getGrupos()
                        .stream()
                        .map(grupo -> objectMapper.convertValue(grupo, GrupoDTO.class))
                        .collect(Collectors.toList())
        );
        return usuarioDTO;
    }

    public UsuarioDTO update(Integer idUsuario, UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        List<GrupoEntity> grupoEntities = new ArrayList<>();
        for (Integer key : usuarioCreateDTO.getGrupos()) {
            grupoEntities.add(grupoRepository.findById(key)
                    .orElseThrow(() -> new RegraDeNegocioException("Grupo não encontrado")));
        }
        UsuarioEntity find = findById(idUsuario);
        find.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));
        find.setLogin(usuarioCreateDTO.getUsuario());
        find.setGrupos(grupoEntities);
        UsuarioEntity update = usuarioRepository.save(find);
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                update.getIdUsuario(),
                update.getLogin(),
                update.getGrupos()
                        .stream()
                        .map(grupo -> objectMapper.convertValue(grupo, GrupoDTO.class))
                        .collect(Collectors.toList())
        );
        return usuarioDTO;
    }

    public void deleteByid(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity find = findById(idUsuario);
        usuarioRepository.delete(find);
    }

    public UsuarioDTO updatePassword(String loginUsuario, UsuarioUpdateDTO usuarioUpdateDTO) throws RegraDeNegocioException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UsuarioEntity find = findByLogin(loginUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("login não encontrado"));
        if (passwordEncoder.matches(usuarioUpdateDTO.getOldSenha(), find.getSenha())) {
            find.setSenha(passwordEncoder.encode(usuarioUpdateDTO.getNewSenha()));
        } else {
            throw new RegraDeNegocioException("Senha incorreta");
        }
        UsuarioEntity update = usuarioRepository.save(find);
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                update.getIdUsuario(),
                update.getLogin(),
                update.getGrupos()
                        .stream()
                        .map(grupo -> objectMapper.convertValue(grupo, GrupoDTO.class))
                        .collect(Collectors.toList())
        );
        return usuarioDTO;
    }
}
