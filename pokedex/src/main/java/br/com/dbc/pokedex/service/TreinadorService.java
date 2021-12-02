package br.com.dbc.pokedex.service;

import br.com.dbc.pokedex.dto.TreinadorCreateDTO;
import br.com.dbc.pokedex.exceptions.RegraDeNegocioException;
import br.com.dbc.pokedex.dto.TreinadorDTO;
import br.com.dbc.pokedex.entity.TreinadorEntity;
import br.com.dbc.pokedex.repository.TreinadorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreinadorService {
    private final TreinadorRepository treinadorRepository;
    private final ObjectMapper objectMapper;

    public TreinadorEntity getTreinadorById(String idTreinador) throws RegraDeNegocioException {
        return treinadorRepository.findById(idTreinador)
                .orElseThrow(() -> new RegraDeNegocioException("Treinador não encontrado"));
    }

    public TreinadorDTO create(TreinadorCreateDTO treinadorCreateDTO) {
        TreinadorEntity entity = objectMapper.convertValue(treinadorCreateDTO, TreinadorEntity.class);
        TreinadorEntity create = treinadorRepository.save(entity);
        return objectMapper.convertValue(create, TreinadorDTO.class);
    }

    public List<TreinadorDTO> list() {
        return treinadorRepository.findAll()
                .stream()
                .map(treinador -> objectMapper.convertValue(treinador, TreinadorDTO.class))
                .collect(Collectors.toList());
    }

    public TreinadorDTO update(String idTreinador, TreinadorCreateDTO treinadorCreateDTO) throws RegraDeNegocioException {
        TreinadorEntity entity = getTreinadorById(idTreinador);
        entity.setNomeCompleto(treinadorCreateDTO.getNomeCompleto());
        entity.setDataNascimento(treinadorCreateDTO.getDataNascimento());
        entity.setSexo(treinadorCreateDTO.getSexo());
        entity.setEmail(treinadorCreateDTO.getEmail());
        TreinadorEntity update = treinadorRepository.save(entity);
        return objectMapper.convertValue(update, TreinadorDTO.class);
    }

    public void delete(String idTreinador) throws RegraDeNegocioException {
        treinadorRepository.delete(getTreinadorById(idTreinador));
    }
}
