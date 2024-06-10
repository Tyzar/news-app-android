package com.assignment.newsapp.domain.usecases

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.wrapper.Either
import com.assignment.newsapp.domain.entities.news.search.SearchNewsParam
import com.assignment.newsapp.domain.entities.news.search.SearchNewsResult
import com.assignment.newsapp.repositories.newsrepository.NewsRepository
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(searchNewsParam: SearchNewsParam): Either<AppError, SearchNewsResult> {
        return newsRepository.searchNewsBy(
            searchNewsParam.copy(
                keyword = searchNewsParam.keyword
                    .ifEmpty { "Indonesia" }
            )
        )
    }
}