package com.dbc.repository;

import com.dbc.entity.HabilidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HabilidadeRepository extends JpaRepository<HabilidadeEntity, Integer> {
    @Modifying
    @Transactional
    @Query(value = "delete from pokemon_habilidade " +
            "where fk_habilidade_id_habilidade = :idHabilidade", nativeQuery = true)
    int deleteAllByIdHabilidade(Integer idHabilidade);
}
