package com.example.ando_hyeon_backend.domain.auth.presentation.dto.response

data class TokenResponse (
    val accessToken: String,
    val refreshToken: String
)
