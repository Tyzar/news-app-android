package com.assignment.newsapp.datasources.remotes.newsapi.responses

import com.assignment.newsapp.core.utils.formatter.DateFmt
import com.assignment.newsapp.domain.entities.news.articles.Article
import com.assignment.newsapp.domain.entities.news.articles.ArticleSource
import kotlinx.serialization.Serializable

@Serializable
data class SearchNewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleRespData>?
)

@Serializable
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

@Serializable
data class SourceRespData(
    val id: String?,
    val name: String?
)

fun ArticleRespData.toEntity(): Article {
    return Article(
        title = title ?: "",
        publishedDate = if (publishedAt == null) null else DateFmt.parseIso(
            publishedAt
        ),
        author = author ?: "",
        description = description ?: "",
        source = ArticleSource(
            name = source?.name ?: ""
        ),
        urlToImage = urlToImage,
        urlToArticle = url,
        content = content ?: ""
    )
}
