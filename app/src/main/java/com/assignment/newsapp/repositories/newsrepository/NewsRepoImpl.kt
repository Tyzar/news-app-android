package com.assignment.newsapp.repositories.newsrepository

import com.assignment.newsapp.core.utils.Either
import com.assignment.newsapp.datasources.remotes.newsapi.NewsApi
import com.assignment.newsapp.entities.news.errors.SearchNewsError
import com.assignment.newsapp.entities.news.search.SearchNewsParam
import com.assignment.newsapp.entities.news.search.SearchNewsResult

class NewsRepoImpl(private val newsApi: NewsApi): NewsRepository {

    override suspend fun searchNewsBy(
        searchNewsParam: SearchNewsParam
    ): Either<SearchNewsError, SearchNewsResult> {

    }
}