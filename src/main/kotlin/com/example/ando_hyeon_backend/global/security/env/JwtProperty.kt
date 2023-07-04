package com.example.ando_hyeon_backend.global.security.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("jwt")
@ConstructorBinding
data class JwtProperty(
    val secretKey: String,
    val accessExpiredAt: Long
)

