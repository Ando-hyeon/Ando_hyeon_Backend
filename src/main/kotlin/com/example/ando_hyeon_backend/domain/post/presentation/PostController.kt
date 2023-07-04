package com.example.ando_hyeon_backend.domain.post.presentation

import com.example.ando_hyeon_backend.domain.post.business.PostService
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.CreatePostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.EditPostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostResponse
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MinimumPostResponse
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService
) {

    @GetMapping("/list")
    fun getPostList(
        @RequestParam("idx") idx: Int,
        @RequestParam("size") size: Int
    ): Page<MinimumPostResponse>{
        return postService.getPostList(idx, size)
    }

    @GetMapping
    fun getDetailPost(
        @RequestParam("id") id: Long
    ): MaximumPostResponse {
        return postService.getDetailPost(id)
    }

    @PostMapping
    fun createPost(
        @RequestBody request: CreatePostRequest
    ){
        postService.createPost(request)
    }

    @PatchMapping
    fun editPost(
        @RequestBody request: EditPostRequest,
        @RequestParam("id") id: Long
    ){
        postService.editPost(request, id)
    }

}
