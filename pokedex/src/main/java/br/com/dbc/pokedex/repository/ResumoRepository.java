package br.com.dbc.pokedex.repository;

import br.com.dbc.pokedex.entity.ResumoEntity;
import br.com.dbc.pokedex.entity.TreinadorEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumoRepository extends MongoRepository<ResumoEntity, String> {
    @Query("{'treinador._id': ?0}")
    ResumoEntity searchResumoByTreinador(ObjectId objectId);


    Boolean existsByTreinador(TreinadorEntity treinador);
}
