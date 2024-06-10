package com.assignment.newsapp.repositories.newsrepository

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.wrapper.Either
import com.assignment.newsapp.domain.entities.news.search.SearchNewsParam
import com.assignment.newsapp.domain.entities.news.search.SearchNewsResult

interface NewsRepository {
    suspend fun searchNewsBy(
        searchNewsParam: SearchNewsParam
    ): Either<AppError, SearchNewsResult>
}
