package com.rabbitmqproject.estoqueproducer.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PrecoDTO implements Serializable {
    private String codigoProduto;
    private double valor;
}