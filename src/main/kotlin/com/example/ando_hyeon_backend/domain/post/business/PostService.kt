package com.example.ando_hyeon_backend.domain.post.business

import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.CreatePostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.EditPostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostResponse
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MinimumPostResponse
import org.springframework.data.domain.Page

interface PostService {

    fun getPostList(idx: Int, size: Int): Page<MinimumPostResponse>
    fun getDetailPost(id: Long): MaximumPostResponse

    fun createPost(request: CreatePostRequest)
    fun editPost(request: EditPostRequest, id: Long)
    fun deletePost(id: Long)

}
