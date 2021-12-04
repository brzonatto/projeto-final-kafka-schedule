package br.com.dbc.pokedex.repository;

import br.com.dbc.pokedex.entity.PokedexEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokedexRepository extends MongoRepository<PokedexEntity, String>{
    @Query("{'pokemons.pokemon.numero': ?0}")
    List<PokedexEntity> searchPokedexWithPokemon(Integer numeroPokemon);
}
