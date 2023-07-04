package com.example.ando_hyeon_backend.infra.clova

import com.example.ando_hyeon_backend.global.exception.data.BusinessException
import com.example.ando_hyeon_backend.global.exception.data.ErrorCode
import com.example.ando_hyeon_backend.global.util.textParsing.ContentTextParsingUtil
import com.example.ando_hyeon_backend.infra.clova.data.ClovaStatementRequest
import com.example.ando_hyeon_backend.infra.clova.data.ClovaStatementResponse
import com.example.ando_hyeon_backend.infra.clova.data.Confidence
import com.example.ando_hyeon_backend.infra.clova.env.NCloudProperty
import com.example.ando_hyeon_backend.infra.ncloud.clova.data.ClovaSummaryRequset
import com.example.ando_hyeon_backend.infra.ncloud.clova.data.ClovaSummaryResponse
import com.example.ando_hyeon_backend.infra.ncloud.clova.data.Document
import com.example.ando_hyeon_backend.infra.ncloud.clova.data.Option
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class ClovaServiceImpl(
    private val parseUtil: ContentTextParsingUtil,
    private val nCloudProperty: NCloudProperty
): ClovaService {
    private val restTemplate: RestTemplate = RestTemplate()

    companion object {
        const val SUMMARY_URL = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize"
        const val STATEMENT_URL = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze"
    }

    override fun extractContent(title: String, content: String): String? {
        val text = parseUtil.extractLink(content).replace("\n", " ")
        if (text.length <= 30) return content

        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers["X-NCP-APIGW-API-KEY-ID"] = nCloudProperty.clientKey
        headers["X-NCP-APIGW-API-KEY"] = nCloudProperty.secretKey

        val request = ClovaSummaryRequset(
            Document(
                title,
                text
            ),
            Option(
                language = "ko",
                summaryCount = 1,
                model = "news",
                tone = null
            )
        )

        val httpEntity = HttpEntity<ClovaSummaryRequset>(request, headers)

        try {
            return restTemplate.exchange(
                SUMMARY_URL,
                HttpMethod.POST,
                httpEntity,
                ClovaSummaryResponse::class.java
            ).body?.summary ?: ""
        } catch (e: HttpClientErrorException) {
            return null
        }
    }

    override fun extractStatement(content: String): Confidence? {
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers["X-NCP-APIGW-API-KEY-ID"] = nCloudProperty.clientKey
        headers["X-NCP-APIGW-API-KEY"] = nCloudProperty.secretKey


        val request = ClovaStatementRequest(
            content
        )

        val httpEntity = HttpEntity<ClovaStatementRequest>(request, headers)

        try {
            return restTemplate.exchange(
                STATEMENT_URL,
                HttpMethod.POST,
                httpEntity,
                ClovaStatementResponse::class.java
            ).body?.document?.confidence?: throw BusinessException(errorCode = ErrorCode.UNDEFINED_ERROR)
        } catch (e: HttpClientErrorException) {
            return null
        }
    }

}
