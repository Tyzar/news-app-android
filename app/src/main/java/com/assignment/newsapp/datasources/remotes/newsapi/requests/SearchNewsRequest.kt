package com.assignment.newsapp.datasources.remotes.newsapi.requests

data class SearchNewsRequest(
    val q: String,
    val from: String,
    val to: String,
    val pageSize: Int,
    val page: Int
)

fun SearchNewsRequest.toQueryParam(): Map<String, Any> {
    return mapOf(
        "q" to q,
        "from" to from,
        "to" to to,
        "pageSize" to pageSize,
        "page" to page
    )
}
