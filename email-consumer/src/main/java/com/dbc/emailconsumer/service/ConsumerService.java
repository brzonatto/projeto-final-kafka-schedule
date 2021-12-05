package com.dbc.emailconsumer.service;

import com.dbc.emailconsumer.dto.EmailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
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
            topicPartitions = {@TopicPartition(topic = "${kafka.topic.send}", partitions = {"0", "1"})},
            containerFactory = "listenerContainerFactoryEarliest"
    )
    public void sendEmail(@Payload String mensagem) throws IOException, MessagingException, TemplateException {
        EmailDTO emailDTO = objectMapper.readValue(mensagem, EmailDTO.class);
        emailService.sendEmail(emailDTO);
        log.info("Mensagem enviada ao destinátario {}", emailDTO.getDestinatario());
    }
}
