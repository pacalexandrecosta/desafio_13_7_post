package com.algaworks.desafio137.post.api.controller;

import com.algaworks.desafio137.post.api.model.CreatePostDto;
import com.algaworks.desafio137.post.api.model.PostOutputDto;
import com.algaworks.desafio137.post.api.model.PostSummaryDto;
import com.algaworks.desafio137.post.domain.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping
    public PagedModel<PostSummaryDto> get(Pageable pageable) {
        return new PagedModel<>(postService.findAll(pageable).map(p -> p.getPostSummaryDto()));
    }

    @GetMapping("/{id}")
    public PostOutputDto get(@PathVariable UUID id) {
        var post = postService.findById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return post.get().getPostOutpostDto();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostOutputDto create(@RequestBody @Valid CreatePostDto dto) {
        return postService.create(dto).getPostOutpostDto();
    }
}
