package com.assignment.newsapp.datasources.remotes.newsapi.implementations.ktor.test

import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.wrapper.Either
import com.assignment.newsapp.datasources.remotes.errors.HttpError
import com.assignment.newsapp.datasources.remotes.errors.NetworkError
import com.assignment.newsapp.datasources.remotes.newsapi.NewsApi
import com.assignment.newsapp.datasources.remotes.newsapi.requests.SearchNewsRequest
import com.assignment.newsapp.datasources.remotes.newsapi.responses.ArticleRespData
import com.assignment.newsapp.datasources.remotes.newsapi.responses.SearchNewsResponse
import com.assignment.newsapp.datasources.remotes.newsapi.responses.SourceRespData
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeNewsApiImpl @Inject constructor() :
    NewsApi {

    companion object MockResult {
        fun createMock200Response(
            pageSize: Int,
            page: Int,
            total: Int
        ): SearchNewsResponse {
            val offset =
                (page - 1) * pageSize + 1

            val articles =
                mutableListOf<ArticleRespData>()
            for (i in offset..<offset + pageSize) {
                articles.add(
                    ArticleRespData(
                        title = "Title $i",
                        author = "Author $i",
                        source = SourceRespData(
                            id = "",
                            name = "Source"
                        ),
                        description = "",
                        publishedAt = "2024-05-20T13:20:00Z",
                        url = "",
                        urlToImage = "https://unsplash.com/photos/blue-circuit-board-jXd2FSvcRr8",
                        content = "Content of article $i"
                    )
                )
            }

            return SearchNewsResponse(
                status = "oke",
                totalResults = 50,
                articles = articles
            )
        }
    }

    override suspend fun searchNews(
        request: SearchNewsRequest
    ): Either<AppError, SearchNewsResponse> {
        delay(2000L)
        return Either.Left(
            HttpError.Unauthorized
        )
        //        return Either.Right(
        //            createMock200Response(
        //                request.pageSize,
        //                request.page,
        //                50
        //            )
        //        )
    }
}