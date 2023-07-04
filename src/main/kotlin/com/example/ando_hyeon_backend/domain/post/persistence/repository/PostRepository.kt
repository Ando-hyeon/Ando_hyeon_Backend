package com.example.ando_hyeon_backend.domain.post.persistence.repository

import com.example.ando_hyeon_backend.domain.post.persistence.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {
}
