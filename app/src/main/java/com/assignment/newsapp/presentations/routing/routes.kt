package com.assignment.newsapp.presentations.routing

import androidx.navigation.NavController

sealed class Route(
    open val path: String
) {
    class ExploreNewsRoute :
        Route("explore")

    class ArticleDetailRoute(val title: String = "") :
        Route(
            "article-detail/{$titleKey}"
        ) {
        companion object ParamKey {
            const val titleKey = "title"
        }

        override fun navigate(
            navController: NavController
        ) {
            navController.navigate("article-detail/${title}")
        }
    }

    open fun navigate(navController: NavController) {
        navController.navigate(path)
    }
}