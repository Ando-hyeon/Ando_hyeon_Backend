package com.example.ando_hyeon_backend.domain.post.presentation.dto.request

import com.example.ando_hyeon_backend.domain.post.persistence.entity.Address


data class CreatePostRequest (
    val title: String,
    val content: String,
    val address: Address
)
