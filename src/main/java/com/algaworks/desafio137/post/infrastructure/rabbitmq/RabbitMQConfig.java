package com.algaworks.desafio137.post.infrastructure.rabbitmq;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {


    public static final String POST_PROCESSING_Q = "text-processor-service.post-processing.v1.q";

    public static final String POST_PROCESSING_RESULT_Q = "post-service.post-processing-result.v1.q";
    public static final String POST_PROCESSING_RESULT_DLQ = "post-service.post-processing-result.v1.dlq";


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter(JsonMapper jsonMapper) {
        return new JacksonJsonMessageConverter(jsonMapper);
    }

    @Bean
    public Queue createPostProcessingResultQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", POST_PROCESSING_RESULT_DLQ);

        return QueueBuilder
                .durable(POST_PROCESSING_RESULT_Q)
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue createPostProcessingResultDeadLetterQueue() {
        return QueueBuilder
                .durable(POST_PROCESSING_RESULT_DLQ)
                .build();
    }
}
