package com.example.ando_hyeon_backend.domain.post.presentation

import com.example.ando_hyeon_backend.domain.post.business.PostService
import com.example.ando_hyeon_backend.domain.post.persistence.entity.PostType
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.CreatePostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.EditPostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostResponse
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MinimumPostResponse
import com.example.ando_hyeon_backend.global.exception.data.BusinessException
import com.example.ando_hyeon_backend.global.exception.data.ErrorCode
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService
) {

    @GetMapping("/list")
    fun getPostList(
        @RequestParam("idx") idx: Int,
        @RequestParam("size") size: Int,
        @RequestParam("type") type: PostType
    ): Page<MinimumPostResponse>{
        return postService.getPostList(idx, size, type)
    }

    @GetMapping
    fun getDetailPost(
        @RequestParam("id") id: Long
    ): MaximumPostResponse {
        return postService.getDetailPost(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPost(
        @RequestBody request: CreatePostRequest,
        @AuthenticationPrincipal user: UserDetails?
    ){
        postService.createPost(request, user?: throw BusinessException(errorCode = ErrorCode.NO_AUTHORIZATION_ERROR))
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun editPost(
        @RequestBody request: EditPostRequest,
        @RequestParam("id") id: Long,
        @AuthenticationPrincipal user: UserDetails?
    ){
        postService.editPost(request, id, user?: throw BusinessException(errorCode = ErrorCode.NO_AUTHORIZATION_ERROR))
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePost(
        @RequestParam id: Long,
        @AuthenticationPrincipal user: UserDetails?
    ) {
        postService.deletePost(id, user?: throw BusinessException(errorCode = ErrorCode.NO_AUTHORIZATION_ERROR))
    }

}
