package com.assignment.newsapp.entities.news.search

import com.assignment.newsapp.entities.news.articles.Article

data class SearchNewsResult(
    val articles: List<Article>,
    val pagingInfo: PagingInfo
)
