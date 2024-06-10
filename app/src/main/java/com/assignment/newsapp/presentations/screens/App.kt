package com.assignment.newsapp.presentations.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assignment.newsapp.presentations.routing.ArticleArgType
import com.assignment.newsapp.presentations.routing.ArticleDetailRoute
import com.assignment.newsapp.presentations.routing.ExploreNewsRoute
import com.assignment.newsapp.presentations.screens.article.ArticleScreen
import com.assignment.newsapp.presentations.screens.explore_news.ExploreNewsScreen
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsEvent
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsVM

@Composable
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController =
            rememberNavController()

        NavHost(
            navController = navController,
            startDestination = ExploreNewsRoute.path
        ) {
            composable(ExploreNewsRoute.path) {
                ExploreNewsScreen(
                    newsVM = hiltViewModel(),
                    navController = navController
                )
            }
            composable(
                ArticleDetailRoute.path,
                arguments = listOf(
                    navArgument(
                        ArticleDetailRoute.articleKey
                    ) {
                        type =
                            ArticleArgType()
                    })
            ) { backStackEntry ->
                val articleJson =
                    backStackEntry.arguments?.getString(
                        ArticleDetailRoute.articleKey
                    ) ?: ""
                val article =
                    ArticleArgType().parseValue(
                        articleJson
                    )

                ArticleScreen(
                    article = article
                ) {
                    navController.popBackStack()
                }
            }
        }
    }
}