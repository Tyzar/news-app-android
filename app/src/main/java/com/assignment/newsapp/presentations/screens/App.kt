package com.assignment.newsapp.presentations.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assignment.newsapp.presentations.routing.Route
import com.assignment.newsapp.presentations.screens.article.ArticleScreen
import com.assignment.newsapp.presentations.screens.explore_news.ExploreNewsScreen
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsEvent
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsVM

@Composable
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController =
            rememberNavController()
        val exploreNewsVM =
            hiltViewModel<ExploreNewsVM>()

        NavHost(
            navController = navController,
            startDestination = Route.ExploreNewsRoute().path
        ) {
            composable(Route.ExploreNewsRoute().path) {
                ExploreNewsScreen(
                    newsVM = exploreNewsVM.apply {
                        notify(
                            ExploreNewsEvent.SearchArticles()
                        )
                    },
                    navController = navController
                )
            }
            composable(
                Route.ArticleDetailRoute().path,
                arguments = listOf(
                    navArgument(Route.ArticleDetailRoute.titleKey) {
                        type =
                            NavType.StringType
                    })
            ) { backStackEntry ->
                val title =
                    backStackEntry.arguments?.getString(
                        Route.ArticleDetailRoute.titleKey
                    ) ?: ""

                ArticleScreen(
                    title = title,
                    newsVM = exploreNewsVM
                ) {
                    navController.popBackStack()
                }
            }
        }
    }
}