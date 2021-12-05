package br.com.dbc.pokedex.service;

import br.com.dbc.pokedex.client.PokeProjetoClient;
import br.com.dbc.pokedex.dto.*;
import br.com.dbc.pokedex.entity.PokedexEntity;
import br.com.dbc.pokedex.entity.TreinadorEntity;
import br.com.dbc.pokedex.exceptions.RegraDeNegocioException;
import br.com.dbc.pokedex.kafka.Producer;
import br.com.dbc.pokedex.repository.PokedexRepository;
import br.com.dbc.pokedex.repository.TreinadorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PokedexService {
    private final PokeProjetoClient pokeProjetoClient;
    private final ObjectMapper objectMapper;
    private final PokedexRepository pokedexRepository;
    private final TreinadorService treinadorService;
    private final TreinadorRepository treinadorRepository;
    private final Producer producer;

    public String auth(LoginDTO loginDTO) {
        return pokeProjetoClient.auth(loginDTO);
    }

    public PokedexEntity getPokedexById(String idPokedex) throws RegraDeNegocioException {
        return pokedexRepository.findById(idPokedex)
                .orElseThrow(() -> new RegraDeNegocioException("Pokédex não encontrada"));
    }

    public List<Document> listPokeDados(String authorizationHeader) {
        return pokeProjetoClient.listPokeDados(authorizationHeader);
    }

    public Integer countTotalPokemons(String authorizationHeader) {
        List<Document> pokemons = listPokeDados(authorizationHeader);
        return pokemons.size();
    }

    public PokedexDTO create(String authorizationHeader, String idTreinador) throws RegraDeNegocioException {
        PokedexEntity entity = new PokedexEntity();
        entity.setQuantidadeDePokemonsExistentes(countTotalPokemons(authorizationHeader));
        entity.setQuantidadePokemonsRevelados(0);
        List<PokeDadosDTO> pokeDadosList = new ArrayList<>();
        entity.setPokemons(pokeDadosList);
        PokedexEntity create = pokedexRepository.save(entity);
        TreinadorEntity treinadorEntity = treinadorService.getTreinadorById(idTreinador);
        treinadorEntity.setPokedexEntity(create);
        treinadorRepository.save(treinadorEntity);
        return objectMapper.convertValue(create, PokedexDTO.class);
    }

    public void updateAllTotalPokemons(Integer totalPokemons) {
        List<TreinadorEntity> treinadores = treinadorRepository.findAll();
        for (TreinadorEntity key : treinadores) {
            PokedexEntity pokedex = key.getPokedexEntity();
            pokedex.setQuantidadeDePokemonsExistentes(totalPokemons);
            pokedexRepository.save(pokedex);
        }
    }

    public List<PokeDadosDTO> listPokemonDadosDTO(String authorizationHeader) {
        return listPokeDados(authorizationHeader)
                .stream()
                .map(document -> {
                    PokeDadosDTO pokeDadosDTO = new PokeDadosDTO();
                    Document docPokemon = objectMapper.convertValue(document.get("pokemon"), Document.class);
                    PokemonDTO pokemonDTO = objectMapper.convertValue(docPokemon, PokemonDTO.class);
                    pokeDadosDTO.setPokemon(pokemonDTO);
                    List docTipo = document.get("tipos", List.class);
                    List<String> tipos = new ArrayList<>();
                    if (docTipo.size() != 0) {
                        for (int i = 0; i < docTipo.size(); i++) {
                            String string = docTipo.get(i).toString().replaceAll("\\{tipo=|}", "");
                            tipos.add(string);
                        }
                    }
                    pokeDadosDTO.setTipos(tipos);
                    List docHabilidade = document.get("habilidades", List.class);
                    List<String> habilidades = new ArrayList<>();
                    if (docHabilidade.size() != 0) {
                        for (int i = 0; i < docHabilidade.size(); i++) {
                            Document doc = objectMapper.convertValue(docHabilidade.get(i), Document.class);
                            habilidades.add(objectMapper.convertValue(doc.get("nome"), String.class));
                        }
                    }
                    pokeDadosDTO.setHabilidades(habilidades);
                    Document docEvolucao = objectMapper.convertValue(document.get("evolucao"), Document.class);
                    EvolucaoNomesDTO evolucaoNomesDTO = objectMapper.convertValue(docEvolucao, EvolucaoNomesDTO.class);
                    pokeDadosDTO.setEvolucao(evolucaoNomesDTO);
                    return pokeDadosDTO;
                })
                .sorted(Comparator.comparing(a -> a.getPokemon().getNumero()))
                .collect(Collectors.toList());
    }

    public PokeDadosDTO getPokeDadosByNumero(Integer numeroPokemon, String authorizationHeader) throws RegraDeNegocioException {
        return listPokemonDadosDTO(authorizationHeader)
                .stream()
                .filter(poke -> poke.getPokemon().getNumero().equals(numeroPokemon))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Pokemon não encontrado"));
    }

    public PokedexDTO revelarPokemon(Integer numeroPokemon, String idTreinador, String authorizationHeader) throws RegraDeNegocioException, JsonProcessingException {
        TreinadorEntity treinadorEntity = treinadorService.getTreinadorById(idTreinador);
        PokedexEntity pokedexEntity = getPokedexById(treinadorEntity.getPokedexEntity().getIdPokedex());
        List<PokeDadosDTO> pokemons = pokedexEntity.getPokemons();
        PokeDadosDTO pokeDadosDTO = getPokeDadosByNumero(numeroPokemon, authorizationHeader);
        if (!pokemons.contains(pokeDadosDTO)) {
            pokemons.add(pokeDadosDTO);
        } else {
            throw new RegraDeNegocioException("Pokemon já revelado");
        }
        pokedexEntity.setPokemons(
                pokemons.stream()
                        .sorted(Comparator.comparing(a -> a.getPokemon().getNumero())).collect(Collectors.toList())
        );
        pokedexEntity.setQuantidadePokemonsRevelados(pokemons.size());

        PokedexEntity pokedexUpdate = pokedexRepository.save(pokedexEntity);
        String mensagem = "Parabéns! Você revelou um Pokémon!" +
                "<br><br>Nome do pokemon é " + pokeDadosDTO.getPokemon().getNome() +
                " E essas são algumas de suas caracteristicas: " +
                "<br><br>Nome: " + pokeDadosDTO.getPokemon().getNome() +
                "<br><br>Número: " + pokeDadosDTO.getPokemon().getNumero() +
                "<br><br>Categoria: " + pokeDadosDTO.getPokemon().getCategoria() +
                "<br><br>Level: " + pokeDadosDTO.getPokemon().getLevel() +
                "<br><br>Tipos: " + pokeDadosDTO.getTipos().toString();
        producer.sendRevelarPokemon(new EmailDTO("Parabéns você revelou um novo Pokémon!" ,treinadorEntity.getNomeCompleto(), treinadorEntity.getEmail(), mensagem));

        return objectMapper.convertValue(pokedexUpdate, PokedexDTO.class);
    }

    public PokedexDadosDTO getDadosPokedex(String idTreinador) throws RegraDeNegocioException {
        TreinadorEntity treinadorEntity = treinadorService.getTreinadorById(idTreinador);
        TreinadorDTO treinadorDTO = objectMapper.convertValue(treinadorEntity, TreinadorDTO.class);
        PokedexEntity pokedexEntity = getPokedexById(treinadorEntity.getPokedexEntity().getIdPokedex());
        PokedexDTO pokedexDTO = objectMapper.convertValue(pokedexEntity, PokedexDTO.class);
        PokedexDadosDTO pokedexDadosDTO = new PokedexDadosDTO();
        pokedexDadosDTO.setTreinador(treinadorDTO);
        pokedexDadosDTO.setPokedex(pokedexDTO);
        return pokedexDadosDTO;
    }


    public void updateAllPokemons(Document document) {
        Document docPokemon = objectMapper.convertValue(document.get("pokemon"), Document.class);
        PokemonDTO pokemonDTO = objectMapper.convertValue(docPokemon, PokemonDTO.class);
        Integer nPokemon = pokemonDTO.getNumero();
        List<PokedexEntity> pokedexes = pokedexRepository.searchPokedexWithPokemon(nPokemon);
        for (PokedexEntity key : pokedexes) {
            PokeDadosDTO delete = key.getPokemons().stream().filter(pokemon -> pokemon.getPokemon().getNumero().equals(nPokemon)).findFirst().orElseThrow();
            key.getPokemons().remove(delete);
            PokeDadosDTO update = new PokeDadosDTO();
            update.setPokemon(pokemonDTO);
            List docTipo = document.get("tipos", List.class);
            List<String> tipos = new ArrayList<>();
            if (docTipo.size() != 0) {
                for (int i = 0; i < docTipo.size(); i++) {
                    String string = docTipo.get(i).toString().replaceAll("\\{tipo=|}", "");
                    tipos.add(string);
                }
            }
            update.setTipos(tipos);
            List docHabilidade = document.get("habilidades", List.class);
            List<String> habilidades = new ArrayList<>();
            if (docHabilidade.size() != 0) {
                for (int i = 0; i < docHabilidade.size(); i++) {
                    Document doc = objectMapper.convertValue(docHabilidade.get(i), Document.class);
                    habilidades.add(objectMapper.convertValue(doc.get("nome"), String.class));
                }
            }
            update.setHabilidades(habilidades);
            Document docEvolucao = objectMapper.convertValue(document.get("evolucao"), Document.class);
            EvolucaoNomesDTO evolucaoNomesDTO = objectMapper.convertValue(docEvolucao, EvolucaoNomesDTO.class);
            update.setEvolucao(evolucaoNomesDTO);
            key.getPokemons().add(update);
            pokedexRepository.save(key);
        }
    }
}
