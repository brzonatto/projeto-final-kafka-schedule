package br.com.dbc.pokedex.repository;

import br.com.dbc.pokedex.entity.TreinadorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreinadorRepository extends MongoRepository<TreinadorEntity, String> {
}
