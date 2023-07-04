package com.example.ando_hyeon_backend.global.exception.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.example.ando_hyeon_backend.global.exception.data.ErrorResponse
import com.example.ando_hyeon_backend.global.exception.data.BusinessException
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class ExceptionFilter(
    private val objectMapper: ObjectMapper
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (err: BusinessException) {
            response.status = err.errorCode.status
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"
            objectMapper.writeValue(response.writer, ErrorResponse(
                err.errorCode.message,
                err.data.toString()
            )
            )
        }
    }


}
