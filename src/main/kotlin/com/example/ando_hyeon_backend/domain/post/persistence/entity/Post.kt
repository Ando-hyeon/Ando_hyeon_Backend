package com.example.ando_hyeon_backend.domain.post.persistence.entity

import com.example.ando_hyeon_backend.domain.auth.persistence.entity.User
import com.example.ando_hyeon_backend.domain.post.presentation.dto.request.EditPostRequest
import com.example.ando_hyeon_backend.domain.post.presentation.dto.response.MaximumPostResponse
import javax.persistence.*


@Entity
@Table(name = "post")
class Post(
    title: String,
    writer: User,
    content: String,
    shortContent: String,
    postType: PostType,
    statementNegative: Int,
    statementPositive: Int,
    statementNeutral: Int,
    address: Address
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    val id: Long? = null

    @Column(name = "title", nullable = false)
    var title: String = title

    @Column(name = "content", nullable = false)
    var content: String = content

    @Column(name = "short_content", nullable = false)
    var shortContent: String = shortContent

    @ManyToOne
    @JoinColumn(name = "writer_email", nullable = true)
    val writer: User = writer

    @Enumerated(EnumType.STRING)
    val postType: PostType = postType

    @Column(name = "statement_neutral", nullable = false)
    val statementNeutral: Int = statementNeutral

    @Column(name = "statement_negative", nullable = false)
    val statementNegative: Int = statementNegative

    @Column(name = "statement_positive", nullable = false)
    val statementPositive: Int = statementPositive

    @Embedded
    var address: Address = address

    fun toMaximumPostResponse(referenceUrl: String?): MaximumPostResponse {
        return MaximumPostResponse(
            this.title,
            this.content,
            this.shortContent,
            this.writer.toUserResponse(),
            this.postType,
            this.statementNeutral,
            this.statementNegative,
            this.statementPositive,
            this.address,
            referenceUrl
        )
    }

    fun edit(r: EditPostRequest) {
        r.title?.let {
            this.title = it
        }
        r.content?.let {
            this.content = it
        }
    }

}
