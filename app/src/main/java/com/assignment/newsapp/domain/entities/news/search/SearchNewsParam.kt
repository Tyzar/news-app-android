package com.assignment.newsapp.domain.entities.news.search

data class SearchNewsParam(
    val keyword: String = "",
    val paging: PagingInfo = PagingInfo()
)
