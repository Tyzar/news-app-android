package com.assignment.newsapp.datasources.remotes.newsapi.requests

data class SearchNewsRequest(
    val query: String,
    val from: String,
    val to: String,
    val pageSize: Int = 10,
    val page: Int
) {
    companion object Param {
        const val queryKey = "q"
        const val from = "from"
        const val to = "to"
        const val pageSize = "pageSize"
        const val page = "page"
    }
}
