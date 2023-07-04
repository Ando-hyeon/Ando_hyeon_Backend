package com.example.ando_hyeon_backend.global.security.configuration

import com.example.ando_hyeon_backend.domain.auth.business.UserDetailsServiceImpl
import com.example.ando_hyeon_backend.global.exception.filter.ExceptionFilter
import com.example.ando_hyeon_backend.global.security.TokenProvider
import com.example.ando_hyeon_backend.global.security.filter.JwtFilter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class FilterConfiguration(
    private val tokenProvider: TokenProvider,
    private val customAuthDetailsService: UserDetailsServiceImpl,
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val jwtFilter = JwtFilter(tokenProvider, customAuthDetailsService)
        val exceptionFilter = ExceptionFilter(objectMapper)
        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionFilter, JwtFilter::class.java)
    }
}

