package com.assignment.newsapp.di

import com.assignment.newsapp.datasources.remotes.newsapi.NewsApi
import com.assignment.newsapp.repositories.newsrepository.NewsRepoImpl
import com.assignment.newsapp.repositories.newsrepository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    companion object {
        @Provides
        @Singleton
        fun provideNewsRepoImpl(newsApi: NewsApi): NewsRepoImpl {
            return NewsRepoImpl(newsApi)
        }
    }

    @Binds
    @Singleton
    abstract fun provideNewsRepository(
        impl: NewsRepoImpl
    ): NewsRepository
}