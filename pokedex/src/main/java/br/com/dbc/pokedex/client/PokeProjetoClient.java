package br.com.dbc.pokedex.client;

import br.com.dbc.pokedex.dto.LoginDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.bson.Document;
import org.springframework.cloud.openfeign.FeignClient;


import java.util.List;

@FeignClient(value = "poke-projeto", url = "localhost:8080")
@Headers({"Content-Type: application/json"})
public interface PokeProjetoClient {
    @RequestLine("POST /auth")
    String auth(LoginDTO loginDTO);

    @RequestLine("GET /pokemon/dados")
    @Headers("Authorization: {authorizationHeader}")
    List<Document> listPokeDados(@Param("authorizationHeader") String authorizationHeader);
}
