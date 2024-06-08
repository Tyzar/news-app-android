package com.assignment.newsapp.datasources.remotes.newsapi

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.Either
import com.assignment.newsapp.datasources.remotes.newsapi.requests.SearchNewsRequest
import com.assignment.newsapp.datasources.remotes.newsapi.responses.SearchNewsResponse

interface NewsApi {
    suspend fun searchNews(
        searchNewsRequest: SearchNewsRequest
    ): Either<AppError, SearchNewsResponse>
}