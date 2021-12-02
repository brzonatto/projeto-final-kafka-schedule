package com.dbc.repository;

import com.dbc.entity.TipoPokemonEntity;
import com.dbc.enums.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPokemonRepository extends JpaRepository<TipoPokemonEntity, Integer> {
    @Query("select count(pt) " +
            "from pokemon_tipo pt " +
            "where pt.pokemon.idPokemon = :idPokemon and pt.tipo = :tipo")
    Integer searchByPokemon(Integer idPokemon, Tipo tipo);

}
