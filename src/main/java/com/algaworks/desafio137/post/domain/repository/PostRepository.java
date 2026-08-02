package com.algaworks.desafio137.post.domain.repository;

import com.algaworks.desafio137.post.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository
        extends JpaRepository<Post, UUID> {
}
