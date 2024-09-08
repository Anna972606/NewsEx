package com.example.newsex.data.repository

import com.example.newsex.data.datasource.TopHeadlinesResponse
import kotlinx.coroutines.flow.Flow

interface NewsProvider {

    fun getTopHeadlines(country: String) : Flow<TopHeadlinesResponse?>
}