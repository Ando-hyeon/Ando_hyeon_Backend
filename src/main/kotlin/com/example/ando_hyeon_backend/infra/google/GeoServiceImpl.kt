package com.example.ando_hyeon_backend.infra.google

import com.example.ando_hyeon_backend.infra.google.data.GeoResponse
import com.example.ando_hyeon_backend.infra.google.env.GoogleProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


@Service
class GeoServiceImpl(
    private val googleProperty: GoogleProperty
): GeoService {
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val objectMapper = ObjectMapper()

    override fun getLatitudeLongitudeByAddress(lat: Double, long: Double): List<GeoResponse> {
        try {
            val surl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + URLEncoder.encode(
                lat.toString(),
                "UTF-8"
            ).toString() + "," + URLEncoder.encode(
                long.toString(),
                "UTF-8"
            ).toString() + "&key=" + googleProperty.clientKey
            val url = URL(surl)
            val `is`: InputStream = url.openConnection().getInputStream()
            val streamReader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            val responseStrBuilder = StringBuilder()
            var inputStr: String?
            while (streamReader.readLine().also { inputStr = it } != null) responseStrBuilder.append(inputStr)
            val jo = JSONObject(responseStrBuilder.toString())
            val results = jo.getJSONArray("results")

            val geoResponseList: List<GeoResponse> = objectMapper.readValue(results.toString(), object : TypeReference<List<GeoResponse>>() {})
            println(geoResponseList)
            return geoResponseList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ArrayList()
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
