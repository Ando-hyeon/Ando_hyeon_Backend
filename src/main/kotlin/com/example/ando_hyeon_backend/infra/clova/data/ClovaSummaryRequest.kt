package com.example.ando_hyeon_backend.infra.ncloud.clova.data


data class ClovaSummaryRequset(
    val document: Document,
    val option: Option
)

data class Document(
    val title: String,
    val content: String
)

data class Option(
    val language: String,
    val model: String?,
    val tone: Int?,
    val summaryCount: Int?
)
