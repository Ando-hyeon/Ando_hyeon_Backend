package com.example.ando_hyeon_backend.domain.post.business

import com.example.ando_hyeon_backend.domain.post.persistence.entity.PostType
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.CreatePostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.EditPostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostResponse
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MinimumPostResponse
import org.springframework.data.domain.Page
import org.springframework.security.core.userdetails.UserDetails

interface PostService {

    fun getPostList(query: String, size: Int, type: PostType): List<MaximumPostResponse>

    fun createPost(request: CreatePostRequest, user: UserDetails)
    fun editPost(request: EditPostRequest, id: Long, user: UserDetails)
    fun deletePost(id: Long, user: UserDetails)

}
