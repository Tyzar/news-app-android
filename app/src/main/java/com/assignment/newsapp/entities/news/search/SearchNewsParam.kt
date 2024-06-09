package com.assignment.newsapp.entities.news.search

data class SearchNewsParam(
    val keyword: String = "",
    val paging: PagingInfo = PagingInfo()
)
