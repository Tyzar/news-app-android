package com.assignment.newsapp.presentations.screens.explore_news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.assignment.newsapp.presentations.routing.ArticleDetailRoute
import com.assignment.newsapp.presentations.screens.explore_news.components.ArticleList
import com.assignment.newsapp.presentations.screens.explore_news.components.ExploreTopBar
import com.assignment.newsapp.presentations.screens.explore_news.components.SearchNewsTopBar
import com.assignment.newsapp.presentations.screens.shared_components.overlay.AppSnackbar
import com.assignment.newsapp.presentations.utils.paginations.PageItem
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsEvent
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsVM

@Composable
fun ExploreNewsScreen(
    newsVM: ExploreNewsVM,
    navController: NavController
) {
    val snackBarHostState =
        remember { SnackbarHostState() }

    val exploreNewsState by newsVM.state.collectAsStateWithLifecycle()

    var searchMode by rememberSaveable {
        mutableStateOf(false)
    }

    if (exploreNewsState.fetchError != null) {
        LaunchedEffect(snackBarHostState) {
            snackBarHostState.showSnackbar(
                message = exploreNewsState.fetchError?.errMsg
                    ?: "Something wrong..."
            )
        }
    }

    LaunchedEffect(exploreNewsState.initialLoad) {
        if (exploreNewsState.initialLoad) {
            newsVM.notify(
                ExploreNewsEvent.Initialize
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { snackbarData ->
                AppSnackbar(
                    snackbarData = snackbarData
                )
            }
        },
        topBar = {
            when {
                searchMode -> SearchNewsTopBar(
                    keyword = exploreNewsState.keyword,
                    onBack = {
                        searchMode =
                            false
                    },
                    onKeywordChanged = { keyword ->
                        newsVM.notify(
                            ExploreNewsEvent.SearchArticles(
                                keyword
                            )
                        )
                    }
                )

                else -> ExploreTopBar(
                    onSearchTap = {
                        searchMode =
                            true
                    })
            }
        }
    ) { contentPadding ->
        ArticleList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    contentPadding
                ),
            articles = exploreNewsState.articleItems,
            isFetching = exploreNewsState.isFetchingData,
            onItemTap = { pageItem ->
                if (pageItem is PageItem.Data) {
                    ArticleDetailRoute().navigate(
                        navController,
                        pageItem.data
                    )
                }
            },
            onRequestNextPage = {
                newsVM.notify(
                    ExploreNewsEvent.DisplayNextPaging
                )
            }
        )
    }
}