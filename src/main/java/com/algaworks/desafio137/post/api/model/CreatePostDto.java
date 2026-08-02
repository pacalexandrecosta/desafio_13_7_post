package com.algaworks.desafio137.post.api.model;

import jakarta.validation.constraints.NotBlank;

public record CreatePostDto(
        @NotBlank
        String title,

        @NotBlank
        String body,

        @NotBlank
        String author) {
}
