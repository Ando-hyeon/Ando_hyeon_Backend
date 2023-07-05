package com.example.ando_hyeon_backend.domain.auth.business

import com.example.ando_hyeon_backend.domain.auth.persistence.entity.User
import com.example.ando_hyeon_backend.domain.auth.persistence.repository.UserRepository
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.LoginRequest
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.SignupRequest
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.response.TokenResponse
import com.example.ando_hyeon_backend.global.exception.data.BusinessException
import com.example.ando_hyeon_backend.global.exception.data.ErrorCode
import com.example.ando_hyeon_backend.global.security.TokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder
): AuthService {

    @Transactional
    override fun signup(request: SignupRequest) {
        userRepository.findById(request.email).orElse(null)?: let {
            val user = User(
                request.email,
                request.name,
                passwordEncoder.encode(request.password)
            )
            userRepository.save(user)
        }
    }

    @Transactional
    override fun login(request: LoginRequest): TokenResponse {
        val user: User = userRepository.findById(request.email)
            .orElseThrow { IllegalArgumentException("유저를 찾을 수 없습니다.") }
        return if (passwordEncoder.matches(request.password, user.password)) {
            tokenProvider.encode(request.email)
        } else {
            throw BusinessException(errorCode = ErrorCode.NOT_MATCHED_ERROR)
        }
    }
}
