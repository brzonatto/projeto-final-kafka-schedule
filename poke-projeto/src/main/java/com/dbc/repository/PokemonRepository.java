package com.dbc.repository;

import com.dbc.entity.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PokemonRepository extends JpaRepository<PokemonEntity, Integer> {
    @Query(value = "select count(*) from pokemon", nativeQuery = true)
    Integer countTotalPokemons();
}
