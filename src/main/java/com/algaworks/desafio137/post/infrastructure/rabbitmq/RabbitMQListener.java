package com.algaworks.desafio137.post.infrastructure.rabbitmq;

import com.algaworks.desafio137.post.api.model.CalculationOutputDto;
import com.algaworks.desafio137.post.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.algaworks.desafio137.post.infrastructure.rabbitmq.RabbitMQConfig.POST_PROCESSING_RESULT_Q;


@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final PostService postService;

    @RabbitListener(queues = POST_PROCESSING_RESULT_Q,
            concurrency = "2-3")
    public void handleTemperatureProcessing(@Payload CalculationOutputDto dto) throws InterruptedException {
        log.info("Post processing received {} {} {}", dto.postId(), dto.wordCount(), dto.calculatedValue());
        postService.updateCalculation(dto);

    }

}
