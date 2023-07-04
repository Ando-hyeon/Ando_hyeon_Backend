package com.example.ando_hyeon_backend.global.security.filter

import com.example.ando_hyeon_backend.domain.auth.business.UserDetailsServiceImpl
import com.example.ando_hyeon_backend.global.exception.data.BusinessException
import com.example.ando_hyeon_backend.global.exception.data.ErrorCode
import com.example.ando_hyeon_backend.global.security.TokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtFilter(
    private val tokenProvider: TokenProvider,
    private val customAuthDetailsService: UserDetailsServiceImpl
): OncePerRequestFilter() {

    companion object{
        const val AUTH = "Authorization"
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val subject: String? = getToken(request)?.let{return@let tokenProvider.getSubjectWithExpiredCheck(it)}
        subject?.let{
            val userDetails = customAuthDetailsService.loadUserByUsername(it)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userDetails, it, userDetails.authorities)
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTH) ?: return null
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        throw BusinessException(errorCode = ErrorCode.NO_AUTHORIZATION_ERROR, bearerToken)
    }
}
