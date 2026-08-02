package com.algaworks.desafio137.post.domain.model;

import com.algaworks.desafio137.post.api.model.PostOutputDto;
import com.algaworks.desafio137.post.api.model.PostSummaryDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(length = 1200)
    private String body;

    private String author;

    private Long wordCount;

    private BigDecimal calculatedValue;

    public String getSummary() {
        return body.lines()
                .limit(3)
                .collect(Collectors.joining("\n"));
    }

    public PostOutputDto getPostOutpostDto() {
        return new PostOutputDto(this.getId(), this.getTitle(), this.getBody(), this.getAuthor(), this.getWordCount(), this.getCalculatedValue());
    }

    public PostSummaryDto getPostSummaryDto() {
        return new PostSummaryDto(this.getId(), this.getTitle(), this.getSummary(), this.getAuthor());
    }

}
