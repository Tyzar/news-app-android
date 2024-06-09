package com.assignment.newsapp.presentations.screens.explore_news

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsEvent
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsVM

@Composable
fun ExploreNewsScreen(newsVM: ExploreNewsVM) {
    var firstLoad = rememberSaveable {
        true
    }

    LaunchedEffect(firstLoad) {
        if (firstLoad) {
            newsVM.notify(
                ExploreNewsEvent.SearchArticles(
                    ""
                )
            )

            firstLoad = false
        }
    }

    Text(text = "News App")
}