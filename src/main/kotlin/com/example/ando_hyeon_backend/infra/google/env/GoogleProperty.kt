package com.example.ando_hyeon_backend.infra.google.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "google")
@ConstructorBinding
data class GoogleProperty (
    val clientKey: String
)
