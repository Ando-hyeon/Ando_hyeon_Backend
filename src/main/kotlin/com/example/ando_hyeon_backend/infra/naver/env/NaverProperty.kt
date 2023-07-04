package com.example.ando_hyeon_backend.infra.naver.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "naver")
@ConstructorBinding
data class NaverProperty (
    val clientId: String,
    val secretKey: String
)
