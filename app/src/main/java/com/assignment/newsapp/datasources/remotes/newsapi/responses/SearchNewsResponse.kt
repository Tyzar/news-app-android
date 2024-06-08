package com.assignment.newsapp.datasources.remotes.newsapi.responses

import kotlinx.serialization.Serializable

@Serializable
data class SearchNewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleRespData>?
)

data class ArticleRespData(
    val source: SourceRespData?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class SourceRespData(
    val id: String?,
    val name: String?
)
