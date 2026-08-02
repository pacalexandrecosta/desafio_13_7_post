package com.algaworks.desafio137.post.domain.service;

import com.algaworks.desafio137.post.api.model.CalculationOutputDto;
import com.algaworks.desafio137.post.api.model.CreateCalculationDto;
import com.algaworks.desafio137.post.api.model.CreatePostDto;
import com.algaworks.desafio137.post.domain.model.Post;
import com.algaworks.desafio137.post.domain.repository.PostRepository;
import com.algaworks.desafio137.post.infrastructure.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final RabbitTemplate rabbitTemplate;

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Optional<Post> findById(UUID id) {
        var post = postRepository.findById(id);
        return post;
    }

    public Post create(CreatePostDto dto) {
        var post = new Post();
        post.setAuthor(dto.author());
        post.setBody(dto.body());
        post.setTitle(dto.title());


        var savedPost = postRepository.save(post);

        var createCalculationDto = new CreateCalculationDto(savedPost.getId(), savedPost.getBody());

        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_PROCESSING_Q
                , createCalculationDto
        );
        log.info("Post sent for processing {} {} {}", savedPost.getId(), savedPost.getBody(), savedPost.getAuthor());


        return savedPost;
    }

    public void updateCalculation(CalculationOutputDto dto) {
        var opt = findById(dto.postId());
        if (opt.isEmpty()) {
            throw new RuntimeException("Post inexistente");
        }
        var post = opt.get();

        post.setWordCount(dto.wordCount());
        post.setCalculatedValue(dto.calculatedValue());

        postRepository.save(post);

    }
}
