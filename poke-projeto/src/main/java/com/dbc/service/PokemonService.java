package com.dbc.service;

import com.dbc.dto.*;
import com.dbc.entity.PokemonEntity;
import com.dbc.entity.StatusEntity;
import com.dbc.exceptions.RegraDeNegocioException;
import com.dbc.repository.HabilidadeRepository;
import com.dbc.repository.PokemonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final HabilidadeRepository habilidadeRepository;
    private final EvolucaoService evolucaoService;
    private final ObjectMapper objectMapper;

    public PokemonDTO create(PokemonCreateDTO pokemonCreateDTO) throws RegraDeNegocioException {
        if (somaStatus(pokemonCreateDTO) >= 580 && pokemonCreateDTO.getRegiaoDominante() == null) {
            throw new RegraDeNegocioException("deve conter região dominate, pois o pokémon é lendário");
        }
        PokemonEntity pokemonEntity = objectMapper.convertValue(pokemonCreateDTO, PokemonEntity.class);
        PokemonEntity pokemonCriado = pokemonRepository.save(pokemonEntity);
        PokemonDTO pokemonDTO = objectMapper.convertValue(pokemonCriado, PokemonDTO.class);
        return pokemonDTO;
    }

    public List<PokemonDTO> list() {
        return pokemonRepository.findAll().stream()
                .map(pokemon -> objectMapper.convertValue(pokemon, PokemonDTO.class))
                .sorted(Comparator.comparing(a -> a.getNumero()))
                .collect(Collectors.toList());
    }

    public PokemonDTO update(Integer idPokemon, PokemonCreateDTO pokemonCreateDTO) throws RegraDeNegocioException {
        if (somaStatus(pokemonCreateDTO) >= 580 && pokemonCreateDTO.getRegiaoDominante() == null) {
            throw new RegraDeNegocioException("deve conter região dominate, pois o pokémon é lendário");
        }
        PokemonEntity find = findById(idPokemon);
        find.setAltura(pokemonCreateDTO.getAltura());
        find.setNome(pokemonCreateDTO.getNome());
        find.setCategoria(pokemonCreateDTO.getCategoria());
        find.setLevel(pokemonCreateDTO.getLevel());
        find.setRegiaoDominante(pokemonCreateDTO.getRegiaoDominante());
        find.setNumero(pokemonCreateDTO.getNumero());
        find.setPeso(pokemonCreateDTO.getPeso());
        find.setStatus(objectMapper.convertValue(pokemonCreateDTO.getStatus(), StatusEntity.class));
        PokemonEntity update = pokemonRepository.save(find);
        PokemonDTO dto = objectMapper.convertValue(update,PokemonDTO.class);
        return dto;
    }

    public Boolean existisSetHabilidadeRepetida(PokemonHabilidadeCreateDTO pokemonHabilidadeCreateDTO) {
        List<Integer> listSemRepetidos = pokemonHabilidadeCreateDTO.getIdHabilidades()
                .stream()
                .distinct()
                .collect(Collectors.toList());
        if (listSemRepetidos.size() != pokemonHabilidadeCreateDTO.getIdHabilidades().size()) {
            return true;
        }
        return false;
    }

    public PokemonHabilidadeDTO setHabilidades(Integer idPokemon, PokemonHabilidadeCreateDTO pokemonHabilidadeCreateDTO) throws RegraDeNegocioException {
        if (existisSetHabilidadeRepetida(pokemonHabilidadeCreateDTO)) {
            throw new RegraDeNegocioException("não deve existir habilidades repetidas para o mesmo pokemon");
        }
        PokemonEntity pokemonEntity = findById(idPokemon);
        pokemonEntity.setHabilidades(
                pokemonHabilidadeCreateDTO.getIdHabilidades()
                        .stream()
                        .map(idHabilidades -> habilidadeRepository.findById(idHabilidades).get())
                        .collect(Collectors.toSet())
        );
        PokemonEntity setHabilities = pokemonRepository.save(pokemonEntity);
        PokemonDTO pokemonDTO = objectMapper.convertValue(setHabilities, PokemonDTO.class);
        PokemonHabilidadeDTO pokemonHabilidadeDTO = new PokemonHabilidadeDTO();
        pokemonHabilidadeDTO.setPokemon(pokemonDTO);
        pokemonHabilidadeDTO.setHabilidades(
                setHabilities.getHabilidades()
                        .stream()
                        .map(habilidade -> objectMapper.convertValue(habilidade, HabilidadeDTO.class))
                        .collect(Collectors.toList())
        );
        return pokemonHabilidadeDTO;
    }

    public void delete(Integer idPokemon) throws RegraDeNegocioException {
        PokemonEntity find = findById(idPokemon);
        if (find.getEvolucaoEntity() != null) {
            evolucaoService.delete(find.getEvolucaoEntity().getIdEvolucao());
        }
        pokemonRepository.delete(find);
    }

    public PokemonEntity findById(Integer id) throws RegraDeNegocioException {
        PokemonEntity entity = pokemonRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("pokemon não encontrado"));
        return entity;
    }

    public Integer somaStatus(PokemonCreateDTO pokemonCreateDTO) {
        return pokemonCreateDTO.getStatus().getAtaque() + pokemonCreateDTO.getStatus().getHp()
                + pokemonCreateDTO.getStatus().getDefesa() + pokemonCreateDTO.getStatus().getEspecialAtaque()
                + pokemonCreateDTO.getStatus().getEspecialDefesa() + pokemonCreateDTO.getStatus().getVelocidade();
    }
}
