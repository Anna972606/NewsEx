package com.example.newsex.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsex.domain.entity.TopHeadlinesModel
import com.example.newsex.presentation.viewmodel.NewsRxViewModel

@Composable
fun EveryThingScreen (){
    val viewModel: NewsRxViewModel = hiltViewModel()
    val topHeadlines: List<TopHeadlinesModel>? by viewModel.topHeadlines.observeAsState()
    NewsUI(topHeadlines){
        viewModel.getEverything(it)
    }
}