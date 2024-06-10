package com.assignment.newsapp.presentations.viewmodels.explore_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.logger.AppLogger
import com.assignment.newsapp.core.utils.wrapper.Either
import com.assignment.newsapp.datasources.remotes.errors.NetworkError
import com.assignment.newsapp.domain.entities.news.search.SearchNewsParam
import com.assignment.newsapp.domain.entities.news.search.SearchNewsResult
import com.assignment.newsapp.domain.entities.news.search.isReachEnd
import com.assignment.newsapp.domain.usecases.SearchNewsUseCase
import com.assignment.newsapp.presentations.utils.paginations.PageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreNewsVM @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {
    private val mState =
        MutableStateFlow(
            ExploreNewsState()
        )
    val state = mState.asStateFlow()

    fun notify(event: ExploreNewsEvent) {
        when (event) {
            ExploreNewsEvent.Initialize -> handleInitialLoad()
            ExploreNewsEvent.DisplayNextPaging -> handleDisplayNextPage()
            is ExploreNewsEvent.SearchArticles -> handleSearchArticles(
                event
            )
        }
    }

    private fun handleInitialLoad() {
        viewModelScope.launch {
            val result =
                searchNewsUseCase.execute(
                    SearchNewsParam()
                )
            handleFetchResult(result)
        }
    }

    private fun handleDisplayNextPage() {
        if (mState.value.isFetchingData) {
            AppLogger.log(msg = "Request fetch next page when fetching data...")
            return
        }

        viewModelScope.launch {
            val fetchState =
                mState.value

            if (fetchState.pagingInfo.isReachEnd(
                    fetchState.articleItems.size
                )
            ) {
                AppLogger.log(msg = "No need to fetch next page...")
                return@launch
            }

            //set fetching status true
            val currArticleItems =
                fetchState.articleItems.toMutableList()
            currArticleItems.add(
                PageItem.Loading()
            )
            mState.value =
                fetchState.copy(
                    isFetchingData = true,
                    articleItems = currArticleItems
                )

            // set next page
            val pagingInfo =
                fetchState.pagingInfo
            val searchNewsParam =
                SearchNewsParam(
                    keyword = fetchState.keyword,
                    paging = pagingInfo.copy(
                        page = pagingInfo.page + 1
                    )
                )

            AppLogger.log(
                msg = "Try to fetch page: ${searchNewsParam.paging.page} " +
                        "with q: ${searchNewsParam.keyword}"
            )
            val fetchResult =
                searchNewsUseCase.execute(
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
            //reset filter and pagin, set fetching true
            val currArticleItems =
                mState.value.articleItems.toMutableList()
            currArticleItems.clear()
            currArticleItems.add(
                PageItem.Loading()
            )
            mState.value =
                ExploreNewsState(
                    initialLoad = mState.value.initialLoad,
                    keyword = event.keyword,
                    isFetchingData = true,
                    articleItems = currArticleItems
                )

            val searchNewsParam =
                SearchNewsParam(
                    keyword = event.keyword
                )
            val searchResult =
                searchNewsUseCase.execute(
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
        //clear loading item
        val fetchState =
            mState.value
        val currArticleItems =
            fetchState.articleItems.toMutableList()
        if (currArticleItems.isNotEmpty()) {
            currArticleItems.removeLast()
        }

        when (fetchResult) {
            is Either.Left -> {
                AppLogger.log(
                    level = AppLogger.LLevel.Error,
                    msg = fetchResult.leftValue.errMsg
                )

                val error =
                    generateErrorMessage(
                        fetchResult.leftValue
                    )

                mState.value =
                    fetchState.copy(
                        initialLoad = false,
                        isFetchingData = false,
                        articleItems = currArticleItems,
                        fetchError = error
                    )
            }

            is Either.Right -> {
                val result =
                    fetchResult.rightValue
                AppLogger.log(msg = "Load data response success. Data fetched num : ${result.articles.size}|${result.pagingInfo.page}")

                currArticleItems.addAll(
                    result.articles.mapIndexed { idx, article ->
                        PageItem.Data(
                            id = idx + currArticleItems.size,
                            data = article
                        )
                    })

                mState.value =
                    fetchState.copy(
                        initialLoad = false,
                        isFetchingData = false,
                        fetchError = null,
                        articleItems = currArticleItems,
                        pagingInfo = result.pagingInfo
                    )
            }
        }
    }

    private fun generateErrorMessage(
        appError: AppError
    ): AppError {
        return when (appError) {
            is NetworkError -> NetworkError(
                "Please check your internet connection"
            )

            else -> AppError("Failed to get data")
        }
    }
}