package com.example.ando_hyeon_backend.infra.clova.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConfigurationProperties(prefix = "ncloud")
@ConstructorBinding
data class NCloudProperty (
    val clientKey: String,
    val secretKey: String
)

