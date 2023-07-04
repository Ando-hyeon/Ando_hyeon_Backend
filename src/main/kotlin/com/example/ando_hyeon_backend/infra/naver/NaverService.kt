package com.example.ando_hyeon_backend.infra.naver

import com.example.ando_hyeon_backend.domain.post.persistence.entity.Post
import com.example.ando_hyeon_backend.infra.naver.data.NaverNewsResponse

interface NaverService {

    fun getNewList(query: String, size: Int): NaverNewsResponse
}
