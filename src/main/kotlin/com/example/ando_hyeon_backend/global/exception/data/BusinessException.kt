package com.example.ando_hyeon_backend.global.exception.data

import com.example.ando_hyeon_backend.global.exception.data.ErrorCode

open class BusinessException(
    val errorCode: ErrorCode,
    val data: String? = errorCode.message
): RuntimeException(errorCode.message) {
}
