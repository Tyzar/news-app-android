package com.assignment.newsapp.presentations.screens.explore_news.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignment.newsapp.core.utils.logger.AppLogger
import com.assignment.newsapp.domain.entities.news.articles.Article
import com.assignment.newsapp.presentations.utils.compose.lazylist.rememberLazyListScroll
import com.assignment.newsapp.presentations.utils.paginations.PageItem

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: List<PageItem<Article>>,
    isFetching: Boolean,
    onItemTap: (article: PageItem<Article>) -> Unit,
    onRequestNextPage: () -> Unit
) {
    val lazyListState =
        rememberLazyListState()

    val scrollState =
        rememberLazyListScroll(
            lazyListState = lazyListState
        )

    LaunchedEffect(scrollState) {
        if (scrollState.reachBottom) {
            AppLogger.log(msg = "Reach Bottom...")
            onRequestNextPage()
        }
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier,
        contentPadding = PaddingValues(
            16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            20.dp
        )
    ) {
        items(
            articles.size,
            key = { idx -> articles[idx].id }) { idx ->
            ArticleItemContainer(
                articleItem = articles[idx]
            ) {
                onItemTap(articles[idx])
            }
        }
    }
}