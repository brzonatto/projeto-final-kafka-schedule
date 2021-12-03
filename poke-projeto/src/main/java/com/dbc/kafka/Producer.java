package com.dbc.kafka;

import com.dbc.dto.PokeDadosDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class Producer {
    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value(value = "${kafka.topic.update}")
    private String send;

    public void sendUpdate(PokeDadosDTO pokeDadosDTO) throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(pokeDadosDTO);
        Message<String> message = MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, send)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .build();
        ListenableFuture<SendResult<String, String>> send = stringKafkaTemplate.send(message);
        send.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Erro ao enviar o update!");
            }
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Update enviado!");
            }
        });
    }

    public void sendUpdateTotalPokemons(Integer total) {
        Message<String> message = MessageBuilder.withPayload(total.toString())
                .setHeader(KafkaHeaders.TOPIC, send)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .build();
        ListenableFuture<SendResult<String, String>> send = stringKafkaTemplate.send(message);
        send.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Erro ao enviar o update!");
            }
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Update enviado!");
            }
        });
    }
}
