package com.assignment.newsapp.presentations.viewmodels.explore_news

sealed class ExploreNewsEvent {
    data object DisplayNextPaging :
        ExploreNewsEvent()

    data class SearchArticles(val keyword: String) :
        ExploreNewsEvent()
}