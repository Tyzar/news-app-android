package com.assignment.newsapp.datasources.remotes.newsapi.requests

data class SearchNewsRequest(
    val query: String,
    val from: String,
    val to: String,
    val pageSize: Int = 10,
    val page: Int,
    val sortBy: String = "publishedAt"
) {
    companion object Param {
        const val sortByKey: String =
            "sortBy"
        const val queryKey = "q"
        const val from = "from"
        const val to = "to"
        const val pageSize = "pageSize"
        const val page = "page"
    }
}
