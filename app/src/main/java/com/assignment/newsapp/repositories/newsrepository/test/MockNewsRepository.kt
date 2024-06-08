package com.assignment.newsapp.repositories.newsrepository.test

import com.assignment.newsapp.core.utils.Either
import com.assignment.newsapp.entities.news.errors.SearchNewsError
import com.assignment.newsapp.entities.news.search.SearchNewsParam
import com.assignment.newsapp.entities.news.search.SearchNewsResult
import com.assignment.newsapp.repositories.newsrepository.NewsRepository

class MockNewsRepository: NewsRepository {

    override suspend fun searchNewsBy(
        searchNewsParam: SearchNewsParam
    ): Either<SearchNewsError, SearchNewsResult> {
        TODO("Not yet implemented")
    }

}