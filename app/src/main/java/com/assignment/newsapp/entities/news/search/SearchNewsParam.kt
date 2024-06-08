package com.assignment.newsapp.entities.news.search

import java.util.Date

data class SearchNewsParam(
    val keyword: String,
    val from: Date,
    val to: Date,
    val paging: PagingInfo
)
