package com.assignment.newsapp.core.utils

sealed class Either<L, R> {
    data class Left<out L>(val leftValue: L)
    data class Right<out R>(val rightValue: R)
}