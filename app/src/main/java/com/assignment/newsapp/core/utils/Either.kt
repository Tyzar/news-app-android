package com.assignment.newsapp.core.utils

sealed class Either<out L, out R> {
    data class Left<out L, out R>(val leftValue: L) :
        Either<L, R>()

    data class Right<out L, out R>(val rightValue: R) :
        Either<L, R>()
}