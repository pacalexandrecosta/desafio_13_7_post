package com.algaworks.desafio137.post.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCalculationDto(
        @NotNull
        UUID postId,

        @NotBlank
        String postBody) {
}
