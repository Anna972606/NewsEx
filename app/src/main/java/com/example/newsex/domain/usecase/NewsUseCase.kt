package com.example.newsex.domain.usecase

import com.example.newsex.domain.entity.TopHeadlinesModel
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {

    fun getTopHeadlines(country: String) : Flow<List<TopHeadlinesModel>>

    fun getEverything(country: String) : Single<List<TopHeadlinesModel>>
}