package com.assignment.newsapp.presentations.viewmodels.explore_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.newsapp.core.errors.AppError
import com.assignment.newsapp.core.utils.Either
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
        viewModelScope.launch {
            //todo: check if reach end page
            if (mState.value.pagingInfo.isReachEnd(
                    mState.value.articles.size
                )
            ) {
                return@launch
            }

            val pagingInfo =
                mState.value.pagingInfo
            mState.value =
                mState.value.copy(
                    isFetchingData = true
                )
            val searchNewsParam =
                SearchNewsParam(
                    keyword = mState.value.keyword,
                    paging = pagingInfo.copy(
                        page = pagingInfo.page + 1
                    )
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

            val searchNewsParam =
                if (event.keyword.isEmpty()) SearchNewsParam()
                else SearchNewsParam(
                    keyword = event.keyword
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
        when (fetchResult) {
            is Either.Left -> {
                mState.value =
                    mState.value.copy(
                        isFetchingData = false,
                        fetchError = fetchResult.leftValue
                    )
            }

            is Either.Right -> {
                val result =
                    fetchResult.rightValue

                val updatedArticles =
                    mState.value.articles.toMutableList()
                updatedArticles.addAll(
                    result.articles
                )

                mState.value =
                    mState.value.copy(
                        isFetchingData = false,
                        fetchError = null,
                        articles = updatedArticles,
                        pagingInfo = result.pagingInfo
                    )
            }
        }
    }
}