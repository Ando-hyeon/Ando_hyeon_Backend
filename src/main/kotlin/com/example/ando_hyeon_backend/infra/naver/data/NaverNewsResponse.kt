package com.example.ando_hyeon_backend.infra.naver.data

data class NaverNewsResponse(
    val display: Int = 0,
    val items: List<Item> = ArrayList(),
    val lastBuildDate: String = "lastBuildDate",
    val start: Int = 0,
    val total: Int = 0
) {
}

data class Item(
    val originallink: String = "",
    val description: String = "",
    val link: String = "",
    val pubDate: String = "",
    val title: String = ""
)
