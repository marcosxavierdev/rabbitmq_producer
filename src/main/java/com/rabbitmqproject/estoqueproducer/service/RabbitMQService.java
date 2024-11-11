package com.rabbitmqproject.estoqueproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage (String queueName, Object message) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        log.info("converted JsonMessage: ".concat(jsonMessage));
        rabbitTemplate.convertAndSend(queueName, jsonMessage);
    }
}
