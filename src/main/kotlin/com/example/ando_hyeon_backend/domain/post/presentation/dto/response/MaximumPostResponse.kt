package com.example.ando_hyeon_backend.domain.post.presentation.dto.response

import com.example.ando_hyeon_backend.domain.post.persistence.entity.PostType

data class MaximumPostResponse (
    val title: String,
    val content: String,
    val shortContent: String,
    val writer: UserResponse,
    val postType: PostType,
    val statementNeutral: Int,
    val statementNegative: Int,
    val statementPositive: Int,
    val region: String,
    val referenceUrl: String?
)
