package com.assignment.newsapp.di

import com.assignment.newsapp.datasources.remotes.newsapi.NewsApi
import com.assignment.newsapp.datasources.remotes.newsapi.implementations.ktor.NewsApiKtorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteSourceModule {
    companion object {
        @Provides
        @Singleton
        fun provideNewsApiKtorImpl(): NewsApiKtorImpl {
            return NewsApiKtorImpl()
        }
    }

    @Binds
    @Singleton
    abstract fun provideNewsApi(
        newsApiKtorImpl: NewsApiKtorImpl
    ): NewsApi
}