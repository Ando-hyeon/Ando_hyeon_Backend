package com.example.ando_hyeon_backend.domain.post.business

import com.example.ando_hyeon_backend.domain.auth.persistence.entity.User
import com.example.ando_hyeon_backend.domain.post.persistence.entity.Post
import com.example.ando_hyeon_backend.domain.post.persistence.entity.PostType
import com.example.ando_hyeon_backend.domain.post.persistence.repository.PostRepository
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.CreatePostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.EditPostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostResponse
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MinimumPostResponse
import com.example.ando_hyeon_backend.global.exception.data.BusinessException
import com.example.ando_hyeon_backend.global.exception.data.ErrorCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class PostServiceImpl(
    private val postRepository: PostRepository
): PostService {

    override fun getPostList(idx: Int, size: Int): Page<MinimumPostResponse> {
        return postRepository.findAll(PageRequest.of(idx, size)).map {
            it.toMinimumPostResponse()
        }
    }

    override fun getDetailPost(id: Long): MaximumPostResponse {
        return (postRepository.findById(id).orElse(null)?: throw BusinessException(errorCode = ErrorCode.PERSISTENCE_DATA_NOT_FOUND_ERROR))
            .toMaximumPostResponse()
    }

    @Transactional
    override fun createPost(request: CreatePostRequest, user: User) {

        postRepository.save(
            Post(
                request.title,
                user,
                request.content,
                request.shortContent,
                PostType.USER
                
            )
        )
    }

    @Transactional
    override fun editPost(request: EditPostRequest, id: Long, user: User) {
        val post = postRepository.findById(id).orElse(null)?: throw BusinessException(errorCode = ErrorCode.PERSISTENCE_DATA_NOT_FOUND_ERROR)
        post.edit(request)
        postRepository.save(post)
    }

    @Transactional
    override fun deletePost(id: Long, user: User) {
        postRepository.deleteById(id)
    }
}
