package com.example.newsex.data.repository

import com.example.newsex.api.NewsApi
import com.example.newsex.data.datasource.TopHeadlinesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi
) : NewsProvider {

    override fun getTopHeadlines(country: String): Flow<TopHeadlinesResponse?> =
        flow {
            emit(newsApi.getTopHeadlines(country))
        }
}