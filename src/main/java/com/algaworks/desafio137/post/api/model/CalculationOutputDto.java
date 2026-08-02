package com.algaworks.desafio137.post.api.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CalculationOutputDto(
        UUID postId,
        Long wordCount,
        BigDecimal calculatedValue) {
}
