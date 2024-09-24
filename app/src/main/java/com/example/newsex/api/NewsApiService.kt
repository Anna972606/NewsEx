package com.example.newsex.api

import com.example.newsex.data.datasource.TopHeadlinesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "62f9c72b89f34eaf97679bbc58be09e1"

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): TopHeadlinesResponse?

    @GET("everything")
   fun getEverything(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Single<TopHeadlinesResponse>
}