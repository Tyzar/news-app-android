package com.assignment.newsapp.presentations.viewmodels.explore_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.logger.AppLogger
import com.assignment.newsapp.core.utils.wrapper.Either
import com.assignment.newsapp.entities.news.search.SearchNewsParam
import com.assignment.newsapp.entities.news.search.SearchNewsResult
import com.assignment.newsapp.entities.news.search.isReachEnd
import com.assignment.newsapp.repositories.newsrepository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreNewsVM @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val mState =
        MutableStateFlow(
            ExploreNewsState()
        )
    val state = mState.asStateFlow()

    fun notify(event: ExploreNewsEvent) {
        when (event) {
            ExploreNewsEvent.DisplayNextPaging -> handleDisplayNextPage()
            is ExploreNewsEvent.SearchArticles -> handleSearchArticles(
                event
            )
        }
    }

    private fun handleDisplayNextPage() {
        if (mState.value.isFetchingData) {
            return
        }

        viewModelScope.launch {
            val fetchState =
                mState.value

            if (fetchState.pagingInfo.isReachEnd(
                    fetchState.articles.size
                )
            ) {
                AppLogger.log(msg = "No need to fetch next page...")
                return@launch
            }

            val pagingInfo =
                fetchState.pagingInfo
            mState.value =
                fetchState.copy(
                    isFetchingData = true
                )
            val searchNewsParam =
                SearchNewsParam(
                    keyword = fetchState.keyword.ifEmpty { "Indonesia" },
                    paging = pagingInfo.copy(
                        page = pagingInfo.page + 1
                    )
                )

            AppLogger.log(
                msg = "Try to fetch page: ${searchNewsParam.paging.page} " +
                        "with q: ${searchNewsParam.keyword}"
            )
            val fetchResult =
                newsRepository.searchNewsBy(
                    searchNewsParam
                )
            handleFetchResult(
                fetchResult
            )
        }
    }

    private fun handleSearchArticles(
        event: ExploreNewsEvent.SearchArticles
    ) {
        viewModelScope.launch {
            mState.value =
                ExploreNewsState(
                    keyword = event.keyword,
                    isFetchingData = true
                )

            val processedKeyword =
                event.keyword.ifEmpty { "Indonesia" }
            val searchNewsParam =
                SearchNewsParam(
                    keyword = processedKeyword
                )
            val searchResult =
                newsRepository.searchNewsBy(
                    searchNewsParam
                )

            handleFetchResult(
                searchResult
            )
        }
    }

    private fun handleFetchResult(
        fetchResult: Either<AppError, SearchNewsResult>
    ) {
        val fetchState =
            mState.value
        when (fetchResult) {
            is Either.Left -> {
                AppLogger.log(
                    level = AppLogger.LLevel.Error,
                    msg = fetchResult.leftValue.errMsg
                )
                mState.value =
                    fetchState.copy(
                        isFetchingData = false,
                        fetchError = fetchResult.leftValue
                    )
            }

            is Either.Right -> {
                val result =
                    fetchResult.rightValue
                AppLogger.log(msg = "Load data response success. Data fetched num : ${result.articles.size}|${result.pagingInfo.page}")

                val updatedArticles =
                    fetchState.articles.toMutableList()
                updatedArticles.addAll(
                    result.articles
                )

                mState.value =
                    fetchState.copy(
                        isFetchingData = false,
                        fetchError = null,
                        articles = updatedArticles,
                        pagingInfo = result.pagingInfo
                    )
            }
        }
    }
}