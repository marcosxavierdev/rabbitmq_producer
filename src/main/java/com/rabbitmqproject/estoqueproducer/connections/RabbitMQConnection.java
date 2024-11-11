package com.rabbitmqproject.estoqueproducer.connections;

import com.rabbitmqproject.estoqueproducer.constants.RabbitMQConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {

    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    private Queue getQueue(String nomeFila){
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange getExchange(){
        return new DirectExchange(RabbitMQConstants.NOME_EXCHANGE);
    }

    private Binding getBinding(Queue queue, DirectExchange exchange){
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void createConnection(){
        Queue estoqueQueue = this.getQueue(RabbitMQConstants.ESTOQUE_QUEUE);
        Queue precoQueue = this.getQueue(RabbitMQConstants.PRECO_QUEUE);

        DirectExchange exchange = this.getExchange();

        Binding estoqueBinding = this.getBinding(estoqueQueue, exchange);
        Binding precoBinding = this.getBinding(precoQueue, exchange);

        // criando as queue/exchange/binding no rabbitmq
        this.amqpAdmin.declareQueue(estoqueQueue);
        this.amqpAdmin.declareQueue(precoQueue);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(estoqueBinding);
        this.amqpAdmin.declareBinding(precoBinding);
    }
}
