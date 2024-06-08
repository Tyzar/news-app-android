package com.assignment.newsapp.entities.news.errors

import com.assignment.newsapp.core.errors.AppError

sealed class SearchNewsError(override val errMsg: String) :
    AppError(errMsg)
