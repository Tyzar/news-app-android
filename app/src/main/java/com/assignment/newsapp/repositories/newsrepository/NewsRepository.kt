package com.assignment.newsapp.repositories.newsrepository

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.Either
import com.assignment.newsapp.entities.news.search.SearchNewsParam
import com.assignment.newsapp.entities.news.search.SearchNewsResult

interface NewsRepository {
    suspend fun searchNewsBy(
        searchNewsParam: SearchNewsParam
    ): Either<AppError, SearchNewsResult>
}
