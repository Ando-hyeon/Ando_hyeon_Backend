package com.example.ando_hyeon_backend.infra.clova

import com.example.ando_hyeon_backend.infra.clova.data.Confidence

interface ClovaService {

    fun extractContent(title: String, content: String): String?
    fun extractStatement(content: String): Confidence?

}
