package com.example.ando_hyeon_backend.domain.post.persistence.repository

import com.example.ando_hyeon_backend.domain.auth.persistence.entity.User
import com.example.ando_hyeon_backend.domain.post.persistence.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface PostRepository: JpaRepository<Post, Long> {

    @Query(
        value = "select * from post where detail_address like %:detailAddress%",
        countQuery = "select count(*) from post where detail_address like %:detailAddress%",
        nativeQuery = true)
    fun findByDetailAddress(@Param(value = "detailAddress") detailAddress: String, pageable: Pageable): Page<Post>

    fun findByWriter(user: User): List<Post>

    fun deleteByTitleAndWriter(title: String, user: User)

    fun findByTitleAndWriter(title: String, writer: User): Optional<Post>
}
