package com.example.ando_hyeon_backend.domain.post.persistence.entity

import com.example.ando_hyeon_backend.domain.auth.persistence.entity.User
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.EditPostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostResponse
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MinimumPostResponse
import javax.persistence.*


@Entity
class Post(
    title: String,
    writer: User,
    content: String,
    shortContent: String,
    postType: PostType,
    statementNegative: Int,
    statementPositive: Int,
    statementNeutral: Int,
    region: String
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    val id: Long? = null

    @Column(name = "title", nullable = false)
    val title: String = title

    @Column(name = "content", nullable = false)
    val content: String = content

    @Column(name = "short_content", nullable = false)
    val shortContent: String = shortContent

    @ManyToOne
    @JoinColumn(name = "writer_email")
    val writer: User = writer

    @Enumerated(EnumType.STRING)
    val postType: PostType = postType

    @Column(name = "statement_neutral", nullable = false)
    val statementNeutral: Int = statementNeutral

    @Column(name = "statement_negative", nullable = false)
    val statementNegative: Int = statementNegative

    @Column(name = "statement_positive", nullable = false)
    val statementPositive: Int = statementPositive

    @OneToOne
    @JoinColumn(name = "file_id", nullable = true)
    val file: File? = null

    @Column(name = "")
    val region: String = region

    fun toMinimumPostResponse(): MinimumPostResponse {
        return MinimumPostResponse()
    }

    fun toMaximumPostResponse(): MaximumPostResponse {
        return MaximumPostResponse()
    }

    fun edit(request: EditPostRequest) {

    }

}
