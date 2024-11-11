package com.rabbitmqproject.estoqueproducer.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmqproject.estoqueproducer.constants.RabbitMQConstants;
import com.rabbitmqproject.estoqueproducer.dto.PrecoDTO;
import com.rabbitmqproject.estoqueproducer.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/preco")
@Slf4j
public class PrecoController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity precoUpdate (@RequestBody PrecoDTO precoDTO) throws JsonProcessingException {
        log.info("Preco de produto atualizado: "
                .concat("codigoProduto: ").concat(precoDTO.getCodigoProduto())
                .concat(", quantidade: ").concat(String.valueOf(precoDTO.getValor()))
        );
        rabbitMQService.sendMessage(RabbitMQConstants.PRECO_QUEUE, precoDTO);
        return ResponseEntity.ok("Preco atualizado com sucesso!");
    }
}
