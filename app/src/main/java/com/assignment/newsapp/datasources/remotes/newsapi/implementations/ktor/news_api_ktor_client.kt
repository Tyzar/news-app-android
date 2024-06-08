package com.assignment.newsapp.datasources.remotes.newsapi.implementations.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

fun createNewsApiKtorClient(): HttpClient {
    return HttpClient(Android) {
        engine {
            connectTimeout = 20000
        }
    }
}