package com.example.ando_hyeon_backend.domain.auth.business

import com.example.ando_hyeon_backend.domain.auth.persistence.repository.UserRepository
import com.example.ando_hyeon_backend.global.exception.data.BusinessException
import com.example.ando_hyeon_backend.global.exception.data.ErrorCode
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl (
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(email: String?): UserDetails {
        return userRepository.findById(email?: throw BusinessException(errorCode = ErrorCode.UNDEFINED_ERROR)).orElse(null)
            ?: throw BusinessException(ErrorCode.PERSISTENCE_DATA_NOT_FOUND_ERROR)
    }
}

