package com.assignment.newsapp.entities.news.articles

import java.util.Date

data class Article(
    val title: String,
    val description: String = "",
    val author: String = "",
    val publishedDate: Date,
    val urlToImage: String? = null,
    val urlToArticle: String? = null,
    val source: ArticleSource = ArticleSource()
)

data class ArticleSource(val name: String = "")
