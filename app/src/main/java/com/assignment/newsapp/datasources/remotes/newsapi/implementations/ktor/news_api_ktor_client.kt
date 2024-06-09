package com.assignment.newsapp.datasources.remotes.newsapi.implementations.ktor

import com.assignment.newsapp.core.utils.logger.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val request_timeout = 30000
fun createNewsApiKtorClient(): HttpClient {
    return HttpClient(Android) {
        expectSuccess = true

        engine {
            connectTimeout =
                request_timeout
            socketTimeout =
                request_timeout
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    AppLogger.log(msg = message)
                }
            }

            level = LogLevel.ALL
            filter { request ->
                request.url.host.contains(
                    "ktor.io"
                )
            }
        }
    }
}