package com.example.ando_hyeon_backend.domain.post.business

import com.example.ando_hyeon_backend.domain.post.persistence.entity.PostType
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.CreatePostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.EditPostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostResponse
import org.springframework.security.core.userdetails.UserDetails

interface PostService {

    fun getMyPostList(user: UserDetails): List<MaximumPostResponse>
    fun getPostList(query: String, size: Int, type: PostType): List<MaximumPostResponse>

    fun createPost(request: CreatePostRequest, user: UserDetails)
    fun editPost(request: EditPostRequest, title: String, user: UserDetails)
    fun deletePost(title: String, user: UserDetails)

}
