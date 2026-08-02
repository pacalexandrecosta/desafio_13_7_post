package com.algaworks.desafio137.post.api.model;

import java.math.BigDecimal;
import java.util.UUID;

public record PostOutputDto(UUID id,
                            String title,
                            String body,
                            String author,
                            Long wordCount,
                            BigDecimal calculatedValue) {

}
