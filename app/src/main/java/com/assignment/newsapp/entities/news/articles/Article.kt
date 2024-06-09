package com.assignment.newsapp.entities.news.articles

import com.assignment.newsapp.core.utils.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Article(
    val title: String,
    val description: String = "",
    val author: String = "",
    @Serializable(with = LocalDateTimeSerializer::class)
    val publishedDate: LocalDateTime? = null,
    val urlToImage: String? = null,
    val urlToArticle: String? = null,
    val source: ArticleSource = ArticleSource(),
    val content: String = ""
)

@Serializable
data class ArticleSource(val name: String = "")
