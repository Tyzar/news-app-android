package com.assignment.newsapp.presentations.utils.paginations

sealed class PageItem<T>(open val id: Int) {
    data class Loading<T>(override val id: Int = -1) :
        PageItem<T>(id)

    data class Data<T>(
        override val id: Int,
        val data: T
    ) :
        PageItem<T>(id)
}