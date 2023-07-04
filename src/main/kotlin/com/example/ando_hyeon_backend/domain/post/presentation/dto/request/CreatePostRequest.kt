package com.example.ando_hyeon_backend.domain.post.presentation.dto.request

data class CreatePostRequest (
    val title: String,
    val content: String,
    val statementNegative: String,
    val statementPositive: String,
    val statementNeutral: String,
    val region: String,

)
