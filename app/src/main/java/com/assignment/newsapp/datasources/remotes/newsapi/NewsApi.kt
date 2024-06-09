package com.assignment.newsapp.datasources.remotes.newsapi

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.wrapper.Either
import com.assignment.newsapp.datasources.remotes.newsapi.requests.SearchNewsRequest
import com.assignment.newsapp.datasources.remotes.newsapi.responses.SearchNewsResponse

interface NewsApi {
    suspend fun searchNews(
        request: SearchNewsRequest
    ): Either<AppError, SearchNewsResponse>
}
