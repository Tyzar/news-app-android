package com.assignment.newsapp.presentations.viewmodels.explore_news

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.domain.entities.news.articles.Article
import com.assignment.newsapp.domain.entities.news.search.PagingInfo
import com.assignment.newsapp.presentations.utils.paginations.PageItem

data class ExploreNewsState(
    //initial load flag
    val initialLoad: Boolean = true,

    //search keyword
    val keyword: String = "",

    //article pagination related props
    val articleItems: List<PageItem<Article>> = emptyList(),
    val pagingInfo: PagingInfo = PagingInfo(),

    //fetching flag
    val isFetchingData: Boolean = false,

    //error flag
    val fetchError: AppError? = null
)