package com.example.newsex.data.repository

import com.example.newsex.data.datasource.TopHeadlinesResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface NewsProvider {

    fun getTopHeadlines(country: String) : Flow<TopHeadlinesResponse?>

    fun getEverything(value: String) : Single<TopHeadlinesResponse>
}