package com.assignment.newsapp.presentations.viewmodels.explore_news

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.entities.news.articles.Article
import com.assignment.newsapp.entities.news.search.PagingInfo

data class ExploreNewsState(
    val keyword: String = "",
    val articles: List<Article> = emptyList(),
    val pagingInfo: PagingInfo = PagingInfo(),
    val isFetchingData: Boolean = false,
    val fetchError: AppError? = null
)