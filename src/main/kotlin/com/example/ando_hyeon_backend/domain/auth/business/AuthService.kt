package com.example.ando_hyeon_backend.domain.auth.business

import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.LoginRequest
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.SignupRequest
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.response.TokenResponse

interface AuthService {
    fun signup(request: SignupRequest)
    fun login(request: LoginRequest): TokenResponse

}
