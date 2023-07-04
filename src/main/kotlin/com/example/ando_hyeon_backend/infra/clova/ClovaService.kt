package com.example.ando_hyeon_backend.infra.clova

interface ClovaService {

    fun extractContent(title: String, content: String): String?
    fun extractStatement(content: String): String?

}
