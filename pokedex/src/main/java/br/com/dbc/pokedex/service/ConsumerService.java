package br.com.dbc.pokedex.service;

import br.com.dbc.pokedex.dto.PokeDadosDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {
    private final ObjectMapper objectMapper;
    private final PokedexService pokedexService;

    @KafkaListener(
            topics = "${kafka.topic.updatePokeDados}",
            groupId = "${kafka.group-id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic.updatePokeDados}", partitions = {"0", "1"})},
            containerFactory = "listenerContainerFactoryEarliest",
            clientIdPrefix = "private2"
    )
    public void consume(@Payload String mensagem, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition) throws JsonProcessingException {
        if (partition == 1) {
            Integer totalPokemons = objectMapper.readValue(mensagem, Integer.class);
            pokedexService.updateAllTotalPokemons(totalPokemons);
            log.info("Update recebido: {}", totalPokemons);
        }

        if (partition == 0) {
            log.info("atualizou o pokemon");
        }

    }


}
