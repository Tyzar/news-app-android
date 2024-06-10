package com.assignment.newsapp.domain.entities.news.search

import com.assignment.newsapp.domain.entities.news.articles.Article

data class SearchNewsResult(
    val articles: List<Article>,
    val pagingInfo: PagingInfo
)
