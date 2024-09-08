package com.example.newsex.domain.usecase

import com.example.newsex.domain.entity.TopHeadlinesModel
import com.example.newsex.data.mapper.NewsMapper
import com.example.newsex.data.repository.NewsProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsUseCaseImpl(
    private val newsProvider: NewsProvider,
    private val newsMapper: NewsMapper
) : NewsUseCase {

    override fun getTopHeadlines(country: String): Flow<List<TopHeadlinesModel>> {
        return newsProvider.getTopHeadlines(country).map {
            newsMapper.transformTopHeadlinesModel(it)
        }
    }
}