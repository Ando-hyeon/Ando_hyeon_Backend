package com.example.ando_hyeon_backend.global.security

import com.example.ando_hyeon_backend.domain.auth.business.UserDetailsServiceImpl
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.response.TokenResponse
import com.example.ando_hyeon_backend.global.exception.data.BusinessException
import com.example.ando_hyeon_backend.global.exception.data.ErrorCode
import com.example.ando_hyeon_backend.global.security.env.JwtProperty
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*


@Component
class TokenProvider(
    private val jwtProperty: JwtProperty,
    private val customAuthDetailsService: UserDetailsServiceImpl
){

    fun encode(subject: String): TokenResponse {
        return TokenResponse(
            Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
                .setSubject(subject)
                .claim("type", "access")
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + (jwtProperty.accessExpiredAt * 1000)))
                .compact()
            ,
            Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
                .claim("type", "refresh")
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + (jwtProperty.refreshExpiredAt * 1000)))
                .compact()
        )
    }

    fun decodeBody(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(jwtProperty.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException) {
            throw BusinessException(ErrorCode.NO_AUTHORIZATION_ERROR, e.message.toString())
        }
    }

    fun getSubjectWithExpiredCheck(token: String): String {
        val body = decodeBody(token)
        val now = Date()
        if (now.after(Date(now.time + body.expiration.time))) throw BusinessException(ErrorCode.NO_AUTHORIZATION_ERROR, token)
        return body.subject
    }

    fun isExpired(token: String): Boolean {
        val body = Jwts.parser().setSigningKey(jwtProperty.secretKey).parseClaimsJwt(token).body
        val now = Date()
        return now.after(Date(now.time + body.expiration.time))
    }

    fun authentication(token: String): Authentication {
        val userDetails: UserDetails = customAuthDetailsService.loadUserByUsername(getSubjectWithExpiredCheck(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

}

