package com.rabbitmqproject.estoqueproducer.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmqproject.estoqueproducer.constants.RabbitMQConstants;
import com.rabbitmqproject.estoqueproducer.dto.EstoqueDTO;
import com.rabbitmqproject.estoqueproducer.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/estoque")
@Slf4j
public class EstoqueController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity estoqueUpdate(@RequestBody EstoqueDTO estoqueDTO) throws JsonProcessingException {
        log.info("Item de estoque atualizado: "
            .concat("codigoProduto: ").concat(estoqueDTO.getCodigoProduto())
            .concat(", quantidade: ").concat(String.valueOf(estoqueDTO.getQuantidade()))
        );
        rabbitMQService.sendMessage(RabbitMQConstants.ESTOQUE_QUEUE, estoqueDTO);
        return ResponseEntity.ok("Estoque atualizado com sucesso!");
    }
}
