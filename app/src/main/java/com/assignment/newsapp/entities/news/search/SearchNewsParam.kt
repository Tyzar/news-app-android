package com.assignment.newsapp.entities.news.search

data class SearchNewsParam(
    val keyword: String = "indonesia",
    val paging: PagingInfo = PagingInfo()
)
