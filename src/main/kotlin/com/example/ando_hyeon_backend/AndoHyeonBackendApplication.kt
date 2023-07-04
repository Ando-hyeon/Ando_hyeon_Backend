package com.example.ando_hyeon_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class AndoHyeonBackendApplication

fun main(args: Array<String>) {
	runApplication<AndoHyeonBackendApplication>(*args)
}
