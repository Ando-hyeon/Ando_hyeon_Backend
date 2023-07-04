package com.example.ando_hyeon_backend.global.util.textParsing

interface ContentTextParsingUtil {

    fun extractLink(content: String): String
    fun getLinkList(str: String): List<String>?

}
