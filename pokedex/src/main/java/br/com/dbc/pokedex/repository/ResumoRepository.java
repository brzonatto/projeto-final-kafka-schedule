package br.com.dbc.pokedex.repository;

import br.com.dbc.pokedex.entity.ResumoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumoRepository extends MongoRepository<ResumoEntity, String> {
}
