package com.example.newsex.api

import retrofit2.Retrofit
import javax.inject.Inject

class NewsApi @Inject constructor(
    retrofitBuilder: Retrofit.Builder
) {
    private val apiService = retrofitBuilder.build().create(NewsApiService::class.java)

    suspend fun getTopHeadlines(country: String) = apiService.getTopHeadlines(country)
}