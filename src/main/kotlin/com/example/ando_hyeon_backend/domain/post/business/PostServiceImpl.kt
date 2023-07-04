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
import com.example.ando_hyeon_backend.infra.clova.ClovaService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val clovaService: ClovaService
): PostService {

    override fun getPostList(idx: Int, size: Int, type: PostType): Page<MinimumPostResponse> {
        return postRepository.findAll(PageRequest.of(idx, size)).map {
            it.toMinimumPostResponse()
        }
    }

    override fun getDetailPost(id: Long): MaximumPostResponse {
        return (postRepository.findById(id).orElse(null)?: throw BusinessException(errorCode = ErrorCode.PERSISTENCE_DATA_NOT_FOUND_ERROR))
            .toMaximumPostResponse()
    }

    @Transactional
    override fun createPost(request: CreatePostRequest, user: UserDetails) {
        val clovaSummary = clovaService.extractContent(request.title, request.content)
            ?: request.title

        val statement = clovaService.extractStatement(clovaSummary)?: throw BusinessException(errorCode = ErrorCode.UNDEFINED_ERROR)

        postRepository.save(
            Post(
                request.title,
                user as User,
                request.content,
                clovaSummary,
                PostType.USER,
                (statement.negative * 100).toInt(),
                (statement.positive * 100).toInt(),
                (statement.neutral * 100).toInt(),
                request.region
            )
        )
    }

    @Transactional
    override fun editPost(request: EditPostRequest, id: Long, user: UserDetails) {
        val post = postRepository.findById(id).orElse(null)?: throw BusinessException(errorCode = ErrorCode.PERSISTENCE_DATA_NOT_FOUND_ERROR)
        post.edit(request)
        postRepository.save(post)
    }

    @Transactional
    override fun deletePost(id: Long, user: UserDetails) {
        postRepository.deleteById(id)
    }
}
