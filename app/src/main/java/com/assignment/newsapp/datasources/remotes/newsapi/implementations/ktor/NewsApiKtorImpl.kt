package com.assignment.newsapp.datasources.remotes.newsapi.implementations.ktor

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.Either
import com.assignment.newsapp.datasources.remotes.newsapi.NewsApi
import com.assignment.newsapp.datasources.remotes.newsapi.requests.SearchNewsRequest
import com.assignment.newsapp.datasources.remotes.newsapi.responses.SearchNewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsApiKtorImpl :
    NewsApi {
    private val httpClient =
        createNewsApiKtorClient()

    override suspend fun searchNews(
        searchNewsRequest: SearchNewsRequest
    ): Either<AppError, SearchNewsResponse> = withContext(Dispatchers.IO) {
        try {
            return@withContext Either.Right(SearchNewsResponse())
        } catch (e: Exception){
            return@withContext Either.Left(AppError(""))
        }
    }
}