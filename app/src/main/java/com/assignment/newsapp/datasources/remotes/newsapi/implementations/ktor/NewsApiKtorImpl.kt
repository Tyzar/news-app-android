package com.assignment.newsapp.datasources.remotes.newsapi.implementations.ktor

import com.assignment.newsapp.BuildConfig
import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.wrapper.Either
import com.assignment.newsapp.datasources.remotes.errors.HttpError
import com.assignment.newsapp.datasources.remotes.errors.NetworkError
import com.assignment.newsapp.datasources.remotes.newsapi.NewsApi
import com.assignment.newsapp.datasources.remotes.newsapi.requests.SearchNewsRequest
import com.assignment.newsapp.datasources.remotes.newsapi.responses.SearchNewsResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class NewsApiKtorImpl @Inject constructor() :
    NewsApi {
    private val httpClient =
        createNewsApiKtorClient()

    companion object Url {
        const val baseUrl =
            "https://newsapi.org"
        const val apiKeyHeader =
            "x-api-key"
        const val everything =
            "/v2/everything"
    }

    override suspend fun searchNews(
        request: SearchNewsRequest
    ): Either<AppError, SearchNewsResponse> =
        withContext(Dispatchers.IO) {
            try {
                val respData =
                    httpClient.get("$baseUrl$everything") {
                        headers {
                            append(
                                apiKeyHeader,
                                BuildConfig.APIKEY
                            )
                        }
                        url {
                            parameters.append(
                                SearchNewsRequest.queryKey,
                                request.query
                            )
                            parameters.append(
                                SearchNewsRequest.pageSize,
                                request.pageSize.toString()
                            )
                            parameters.append(
                                SearchNewsRequest.page,
                                request.page.toString()
                            )
                            if (request.from.isNotEmpty()) {
                                parameters.append(
                                    SearchNewsRequest.from,
                                    request.from
                                )
                            }
                            if (request.to.isNotEmpty()) {
                                parameters.append(
                                    SearchNewsRequest.to,
                                    request.to
                                )
                            }
                        }
                    }
                        .body<SearchNewsResponse>()

                return@withContext Either.Right(
                    respData
                )
            } catch (e: ClientRequestException) {
                val response =
                    e.response
                return@withContext when (e.response.status) {
                    HttpStatusCode.Unauthorized -> Either.Left(
                        HttpError.Unauthorized
                    )

                    else -> Either.Left(
                        HttpError.Other(
                            response.status.value,
                            e.message
                        )
                    )
                }
            } catch (e: ServerResponseException) {
                return@withContext Either.Left(
                    HttpError.ServerError
                )
            } catch (e: RedirectResponseException) {
                return@withContext Either.Left(
                    HttpError.Other(
                        e.response.status.value,
                        e.message
                    )
                )
            } catch (e: Exception) {
                if (e is UnknownHostException || e is SocketTimeoutException) {
                    return@withContext Either.Left(
                        NetworkError("Please check your connection")
                    )
                }
                return@withContext Either.Left(
                    AppError("Failed to get news")
                )
            }
        }
}