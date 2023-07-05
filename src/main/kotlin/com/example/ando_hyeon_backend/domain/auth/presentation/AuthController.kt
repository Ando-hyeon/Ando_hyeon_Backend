package com.example.ando_hyeon_backend.domain.auth.presentation

import com.example.ando_hyeon_backend.domain.auth.business.AuthService
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.LoginRequest
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.SignupRequest
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.response.TokenResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @RequestBody request: SignupRequest
    ) {
        authService.signup(request)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): TokenResponse {
        return authService.login(request)
    }

}
