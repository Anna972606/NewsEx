package com.example.newsex.di

import com.example.newsex.api.NewsApi
import com.example.newsex.data.mapper.NewsMapper
import com.example.newsex.data.repository.NewsProvider
import com.example.newsex.data.repository.NewsRepository
import com.example.newsex.domain.usecase.NewsUseCase
import com.example.newsex.domain.usecase.NewsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideNewsUseCase(
        newsProvider: NewsProvider,
        newsMapper: NewsMapper
    ): NewsUseCase = NewsUseCaseImpl(newsProvider, newsMapper)

    @Provides
    fun provideNewsRepository(
        newsApi: NewsApi
    ) : NewsProvider = NewsRepository(newsApi)

    @Provides
    fun provideNewsMapper() = NewsMapper()
}