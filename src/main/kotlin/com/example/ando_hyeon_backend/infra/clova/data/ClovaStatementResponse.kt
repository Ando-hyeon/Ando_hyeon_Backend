package com.example.ando_hyeon_backend.infra.clova.data

data class ClovaStatementResponse(
    val document: Document,
    val sentences: List<Sentence>
)
data class Confidence(
    val negative: Double,
    val neutral: Double,
    val positive: Double
)

data class Document(
    val confidence: Confidence,
    val sentiment: String
)

data class Highlight(
    val length: Int,
    val offset: Int
)

data class Sentence(
    val confidence: Confidence,
    val content: String,
    val highlights: List<Highlight>,
    val length: Int,
    val offset: Int,
    val sentiment: String
)
