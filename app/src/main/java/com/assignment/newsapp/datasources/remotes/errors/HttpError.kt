package com.assignment.newsapp.datasources.remotes.errors

import com.assignment.newsapp.core.errors.AppError

sealed class HttpError(
    open val statusCode: Int,
    override val errMsg: String
) :
    AppError(errMsg) {
    data object Unauthorized :
        HttpError(
            statusCode = 401,
            errMsg = "Unauthorized"
        )

    data object ServerError :
        HttpError(
            statusCode = 500,
            errMsg = "Internal Server Error"
        )

    data class Other(
        override val statusCode: Int,
        override val errMsg: String
    ) : HttpError(statusCode, errMsg)
}