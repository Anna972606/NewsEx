package com.example.newsex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsex.domain.entity.TopHeadlinesModel
import com.example.newsex.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private var _topHeadlines = MutableLiveData<List<TopHeadlinesModel>>()
    val topHeadlines: LiveData<List<TopHeadlinesModel>> = _topHeadlines

    init {
        getTopHeadlines(DEFAULT_COUNTRY)
    }

    fun getTopHeadlines(country: String) {
        viewModelScope.launch {
            newsUseCase.getTopHeadlines(country.ifEmpty { DEFAULT_COUNTRY })
                .flowOn(Dispatchers.IO)
                .collect {
                    _topHeadlines.postValue(it)
                }
        }
    }

    companion object {
        private const val DEFAULT_COUNTRY = "US"
    }
}