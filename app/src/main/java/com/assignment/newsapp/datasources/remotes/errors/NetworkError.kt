package com.assignment.newsapp.datasources.remotes.errors

import com.assignment.newsapp.core.errors.AppError

data class NetworkError(override val errMsg: String) :
    AppError(errMsg)