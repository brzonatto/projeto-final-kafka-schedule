package br.com.dbc.pokedex.service;

import br.com.dbc.pokedex.dto.NumeroNomeDTO;
import br.com.dbc.pokedex.dto.PokeDadosDTO;
import br.com.dbc.pokedex.dto.ResumoFinalDTO;
import br.com.dbc.pokedex.dto.EmailDTO;
import br.com.dbc.pokedex.entity.PokedexEntity;
import br.com.dbc.pokedex.entity.ResumoEntity;
import br.com.dbc.pokedex.entity.TreinadorEntity;
import br.com.dbc.pokedex.exceptions.RegraDeNegocioException;
import br.com.dbc.pokedex.kafka.Producer;
import br.com.dbc.pokedex.repository.ResumoRepository;
import br.com.dbc.pokedex.repository.TreinadorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumoService {
    private final ResumoRepository resumoRepository;
    private final Producer producer;
    private final ObjectMapper objectMapper;
    private final TreinadorRepository treinadorRepository;


    public void salvarResumoPokedex() {
        List<TreinadorEntity> treinadores = treinadorRepository.findAll();
        for (TreinadorEntity key :treinadores) {
            if (resumoRepository.existsByTreinador(key)) {
                ResumoEntity resumoExistente = resumoRepository.searchResumoByTreinador(new ObjectId(key.getIdTreinador()));
                resumoExistente.setDataResumo(LocalDate.now());
                resumoExistente.setPokedex(key.getPokedexEntity());
                resumoExistente.setTreinador(key);
                resumoExistente.setTotalTreinadores(treinadores.size());
                resumoRepository.save(resumoExistente);
            } else {
                PokedexEntity pokedex = key.getPokedexEntity();
                ResumoEntity resumo = new ResumoEntity();
                resumo.setDataResumo(LocalDate.now());
                resumo.setPokedex(pokedex);
                resumo.setTreinador(key);
                resumo.setTotalTreinadores(treinadores.size());
                resumoRepository.save(resumo);
            }
        }
    }

    public void enviarResumoPokedex() throws RegraDeNegocioException, JsonProcessingException {
        List<ResumoEntity> resumos = resumoRepository.findAll();
        for (ResumoEntity key : resumos) {
            ResumoFinalDTO resumoFinal = new ResumoFinalDTO();
            resumoFinal.setDataResumo(key.getDataResumo());
            resumoFinal.setTotalPokemons(key.getPokedex().getQuantidadeDePokemonsExistentes());
            resumoFinal.setTotalTreinadores(key.getTotalTreinadores());
            resumoFinal.setEmail(key.getTreinador().getEmail());
            TreinadorEntity treinador = treinadorRepository.findById(key.getTreinador().getIdTreinador()).orElseThrow(() -> new RegraDeNegocioException("Treinador não encontrado"));
            PokedexEntity pokedex = treinador.getPokedexEntity();
            resumoFinal.setTotalPokemonsReveladosHoje(pokedex.getQuantidadePokemonsRevelados() - key.getPokedex().getQuantidadePokemonsRevelados());
            List<NumeroNomeDTO> numeroNomeReveladosHoje = new ArrayList<>();
            for (PokeDadosDTO key2 : pokedex.getPokemons()) {
                if (!key.getPokedex().getPokemons().contains(key2)) {
                    numeroNomeReveladosHoje.add(new NumeroNomeDTO(key2.getPokemon().getNumero(), key2.getPokemon().getNome()));
                }
            }
            resumoFinal.setPokemonsReveladosHoje(numeroNomeReveladosHoje);
            resumoFinal.setTotalPokemonsRevelados(key.getPokedex().getQuantidadePokemonsRevelados() + numeroNomeReveladosHoje.size());
            resumoFinal.setNomeTreinador(key.getTreinador().getNomeCompleto());
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setDestinatario(treinador.getEmail());
            emailDTO.setNomeTreinador(treinador.getNomeCompleto());
            emailDTO.setMensagem(
                    "Esse é seu Resumo de " + resumoFinal.getDataResumo().format(DateTimeFormatter.ofPattern("dd/MM/yy")) +
                            "<br><br> Total de treinadores no mundo: " + resumoFinal.getTotalTreinadores() +
                            "<br>Total de Pokémons no mundo: " + resumoFinal.getTotalPokemons() +
                            "<br>Total de Pokémons Revelados: " + resumoFinal.getTotalPokemonsRevelados() +
                            "<br>Número de Pokémons revelados hoje: " + resumoFinal.getTotalPokemonsReveladosHoje() +
                            "<br><br> Esses foram os Pokémons que você revelou hoje: <br>" +
                            numeroNome(resumoFinal.getPokemonsReveladosHoje())
            );
            emailDTO.setAssunto("Resumo do dia " + resumoFinal.getDataResumo().format(DateTimeFormatter.ofPattern("dd/MM/yy")) + "!");
            producer.sendResumo(emailDTO);
        }
    }

    public String numeroNome(List<NumeroNomeDTO> list) {
        String string = "";
        for (NumeroNomeDTO key : list) {
            string += key.getNumero() + " - " + key.getNome() + "<br>";
        }
        return string;
    }
}
