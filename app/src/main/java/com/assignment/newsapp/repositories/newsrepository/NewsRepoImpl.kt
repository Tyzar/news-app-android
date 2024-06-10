package com.assignment.newsapp.repositories.newsrepository

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.logger.AppLogger
import com.assignment.newsapp.core.utils.wrapper.Either
import com.assignment.newsapp.datasources.remotes.newsapi.NewsApi
import com.assignment.newsapp.datasources.remotes.newsapi.requests.SearchNewsRequest
import com.assignment.newsapp.datasources.remotes.newsapi.responses.toEntity
import com.assignment.newsapp.domain.entities.news.search.PagingInfo
import com.assignment.newsapp.domain.entities.news.search.SearchNewsParam
import com.assignment.newsapp.domain.entities.news.search.SearchNewsResult
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {

    override suspend fun searchNewsBy(
        searchNewsParam: SearchNewsParam
    ): Either<AppError, SearchNewsResult> {
        AppLogger.log(msg = "Search keyword: ${searchNewsParam.keyword}")

        //default limit oldest articles published to 1 week ago
        val to = LocalDateTime.now()
        val from = LocalDateTime.now()
            .minusWeeks(1)

        val request = SearchNewsRequest(
            query = searchNewsParam.keyword,
            page = searchNewsParam.paging.page,
            from = from.format(
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            ),
            to = to.format(
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            )
        )
        val fetchResult =
            newsApi.searchNews(request)
        return when (fetchResult) {
            is Either.Left -> Either.Left(
                fetchResult.leftValue
            )

            is Either.Right -> {
                val respData =
                    fetchResult.rightValue
                val articles =
                    respData.articles?.map { articleRespData ->
                        articleRespData.toEntity()
                    } ?: emptyList()
                Either.Right(
                    SearchNewsResult(
                        articles = articles,
                        pagingInfo = PagingInfo(
                            page = searchNewsParam.paging.page,
                            totalResultNum = respData.totalResults
                                ?: 0
                        )
                    )
                )
            }
        }
    }


}