package com.assignment.newsapp.presentations.routing

import android.os.Bundle
import androidx.navigation.NavController
import com.assignment.newsapp.core.utils.serializer.jsonEncode
import com.assignment.newsapp.entities.news.articles.Article

interface Route<T> {
    fun navigate(
        navController: NavController,
        arg: T
    )
}

class ExploreNewsRoute :
    Route<Unit> {
    companion object {
        const val path = "explore"
    }

    override fun navigate(
        navController: NavController,
        arg: Unit
    ) {
        navController.navigate(path)
    }
}

class ArticleDetailRoute :
    Route<Article> {
    companion object {
        const val articleKey =
            "article"
        const val path =
            "article-detail/{$articleKey}"
    }

    override fun navigate(
        navController: NavController,
        arg: Article
    ) {
        navController.navigate(
            path.replace(
                "{$articleKey}",
                jsonEncode(arg)
            )
        )
    }
}