package com.example.ando_hyeon_backend.domain.post.presentation

import com.example.ando_hyeon_backend.domain.post.business.PostService
import com.example.ando_hyeon_backend.domain.post.persistence.entity.PostType
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.CreatePostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.EditPostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostListResponse
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostResponse
import com.example.ando_hyeon_backend.global.exception.data.BusinessException
import com.example.ando_hyeon_backend.global.exception.data.ErrorCode
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

    @GetMapping("/me")
    fun getMyPostList(
        @AuthenticationPrincipal user: UserDetails?
    ): MaximumPostListResponse {
        return MaximumPostListResponse(
            postService.getMyPostList(user?: throw BusinessException(errorCode = ErrorCode.NO_AUTHORIZATION_ERROR))
        )
    }

    @GetMapping("/list")
    fun getPostList(
        @RequestParam("region") region: String,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("type") type: PostType
    ): MaximumPostListResponse {
        return MaximumPostListResponse(postService.getPostList(region, size, type))
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
        @RequestParam("title") title: String,
        @AuthenticationPrincipal user: UserDetails?
    ){
        postService.editPost(request, title, user?: throw BusinessException(errorCode = ErrorCode.NO_AUTHORIZATION_ERROR))
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePost(
        @RequestParam title: String,
        @AuthenticationPrincipal user: UserDetails?
    ) {
        postService.deletePost(title, user?: throw BusinessException(errorCode = ErrorCode.NO_AUTHORIZATION_ERROR))
    }

}
