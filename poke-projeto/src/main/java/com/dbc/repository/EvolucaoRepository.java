package com.dbc.repository;

import com.dbc.entity.EvolucaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EvolucaoRepository extends JpaRepository<EvolucaoEntity,Integer> {
    @Query(value = "select count(e) " +
            "from evolucao e " +
            "where e.estagioUm.idPokemon = :idPokemon " +
            "or e.estagioDois.idPokemon = :idPokemon " +
            "or e.estagioTres.idPokemon = :idPokemon")
    Integer searchByPokemon(Integer idPokemon);
}
