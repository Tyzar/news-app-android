package com.assignment.newsapp.presentations.utils.compose.lazylist

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

data class LazyListScrollState(val reachBottom: Boolean = false)

@Composable
fun rememberLazyListScroll(lazyListState: LazyListState): LazyListScrollState {
    val scrollState by remember {
        derivedStateOf {
            val layoutInfo =
                lazyListState.layoutInfo
            val reachBottom = when {
                lazyListState.layoutInfo.visibleItemsInfo.isEmpty() -> false
                else -> layoutInfo.visibleItemsInfo
                    .last().index == layoutInfo.totalItemsCount - 1
            }
            LazyListScrollState(
                reachBottom = reachBottom
            )
        }
    }

    return scrollState
}