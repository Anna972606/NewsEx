package com.example.newsex.data.datasource

data class TopHeadlinesResponse(
    val articles: List<ArticleResponse>?,
    val status: String?,
    val totalResults: Int?
)

data class ArticleResponse(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceResponse?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

data class SourceResponse(
    val id: String?,
    val name: String?
)
