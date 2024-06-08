package com.assignment.newsapp.datasources.remotes.errors

import com.assignment.newsapp.core.errors.AppError

sealed class HttpError(
    val statusCode: Int,
    override val errMsg: String
) :
    AppError(errMsg) {
    data class Unauthorized(override val errMsg: String = "Unauthorized") :
        HttpError(
            statusCode = 401,
            errMsg = errMsg
        )

    data object InternalServerError :
        HttpError(
            statusCode = 500,
            errMsg = "Internal Server Error"
        )
}