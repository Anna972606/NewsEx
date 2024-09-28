package com.example.newsex.data.repository

import com.example.newsex.api.NewsApi
import com.example.newsex.data.datasource.TopHeadlinesResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi
) : NewsProvider {

    override fun getTopHeadlines(country: String): Flow<TopHeadlinesResponse?> =
        flow {
            emit(
                try {
                    newsApi.getTopHeadlines(country)
                } catch (e: HttpException) {
                    null
                }
            )
        }

    override fun getEverything(value: String): Single<TopHeadlinesResponse> {
        return newsApi.getEverything(value)
    }
}