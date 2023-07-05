package com.example.ando_hyeon_backend.domain.auth.presentation.dto.request

data class SignupRequest (
    val name: String,
    val email: String,
    val password: String
)
