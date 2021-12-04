package com.dbc.emailconsumer.service;

import com.dbc.emailconsumer.dto.KafkaDTO;
import com.dbc.emailconsumer.dto.RevelarDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @KafkaListener(
            topics = "${kafka.topic.send}",
            groupId = "${kafka.topic.group-id}",
            containerFactory = "listenerContainerFactoryEarliest"
    )
    public void consumeMensagem(@Payload String mensagem) throws IOException, MessagingException, TemplateException {
        RevelarDTO revelarDTO = objectMapper.readValue(mensagem, RevelarDTO.class);
        emailService.sendEmailPessoa(revelarDTO);
        log.info("Mensagem enviada ao destinátario {}", revelarDTO.getDestinatario());
    }
}
