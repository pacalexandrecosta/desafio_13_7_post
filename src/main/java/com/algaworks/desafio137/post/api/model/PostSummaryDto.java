package com.algaworks.desafio137.post.api.model;

import java.util.UUID;

public record PostSummaryDto(UUID id, String title, String summary, String author) {


}
