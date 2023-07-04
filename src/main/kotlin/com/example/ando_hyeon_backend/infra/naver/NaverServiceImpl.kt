package com.example.ando_hyeon_backend.infra.naver

import com.example.ando_hyeon_backend.domain.post.persistence.entity.Post
import com.example.ando_hyeon_backend.infra.naver.data.NaverNewsResponse
import com.example.ando_hyeon_backend.infra.naver.env.NaverProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


@Service
class NaverServiceImpl(
    private val naverProperty: NaverProperty
): NaverService {

    private val objectMapper = ObjectMapper()

    override fun getNewList(query: String, size: Int): NaverNewsResponse {
        val clientId = naverProperty.clientId
        val clientSecret = naverProperty.secretKey
        var text: String? = null
        text = try {
            URLEncoder.encode(query, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("검색어 인코딩 실패", e)
        }
        val apiURL = "https://openapi.naver.com/v1/search/news.json?query=$text&display=$size"
        val requestHeaders: MutableMap<String, String> = HashMap()
        requestHeaders["X-Naver-Client-Id"] = clientId
        requestHeaders["X-Naver-Client-Secret"] = clientSecret
        return objectMapper.readValue(get(apiURL, requestHeaders), NaverNewsResponse::class.java)
    }


    private operator fun get(apiUrl: String, requestHeaders: Map<String, String>): String {
        val con = connect(apiUrl)
        return try {
            con.requestMethod = "GET"
            for ((key, value) in requestHeaders) {
                con.setRequestProperty(key, value)
            }
            val responseCode: Int = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                readBody(con.inputStream)
            } else { // 오류 발생
                readBody(con.errorStream)
            }
        } catch (e: IOException) {
            throw RuntimeException("API 요청과 응답 실패", e)
        } finally {
            con.disconnect()
        }
    }


    private fun connect(apiUrl: String): HttpURLConnection {
        return try {
            val url = URL(apiUrl)
            url.openConnection() as HttpURLConnection
        } catch (e: MalformedURLException) {
            throw RuntimeException("API URL이 잘못되었습니다. : $apiUrl", e)
        } catch (e: IOException) {
            throw RuntimeException("연결이 실패했습니다. : $apiUrl", e)
        }
    }


    private fun readBody(body: InputStream): String {
        val streamReader = InputStreamReader(body)
        try {
            BufferedReader(streamReader).use { lineReader ->
                val responseBody = StringBuilder()
                var line: String?
                while (lineReader.readLine().also { line = it } != null) {
                    responseBody.append(line)
                }
                return responseBody.toString()
            }
        } catch (e: IOException) {
            throw RuntimeException("API 응답을 읽는 데 실패했습니다.", e)
        }
    }
}
