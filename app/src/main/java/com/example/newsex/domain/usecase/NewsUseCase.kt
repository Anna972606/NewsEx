package com.example.newsex.domain.usecase

import com.example.newsex.domain.entity.TopHeadlinesModel
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {

    fun getTopHeadlines(country: String) : Flow<List<TopHeadlinesModel>>
}