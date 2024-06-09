package com.assignment.newsapp.entities.news.search

data class PagingInfo(
    val page: Int = 1,
    val totalResultNum: Int = 0
)

fun PagingInfo.isReachEnd(currArticleNum: Int): Boolean {
    return currArticleNum >= totalResultNum
}
