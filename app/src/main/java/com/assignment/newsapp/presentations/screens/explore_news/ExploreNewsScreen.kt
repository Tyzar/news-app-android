package com.assignment.newsapp.presentations.screens.explore_news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.assignment.newsapp.presentations.routing.Route
import com.assignment.newsapp.presentations.screens.explore_news.components.ArticleList
import com.assignment.newsapp.presentations.screens.explore_news.components.ExploreTopBar
import com.assignment.newsapp.presentations.screens.explore_news.components.SearchNewsTopBar
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsEvent
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsVM

@Composable
fun ExploreNewsScreen(
    newsVM: ExploreNewsVM,
    navController: NavController
) {
    val exploreNewsState by newsVM.state.collectAsStateWithLifecycle()

    var searchMode by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
            articles = exploreNewsState.articles,
            isFetching = exploreNewsState.isFetchingData,
            onItemTap = { article ->
                Route.ArticleDetailRoute(
                    title = article.title
                ).navigate(
                    navController
                )
            },
            onRequestNextPage = {
                newsVM.notify(
                    ExploreNewsEvent.DisplayNextPaging
                )
            }
        )
    }
}