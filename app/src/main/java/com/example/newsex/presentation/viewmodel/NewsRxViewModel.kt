package com.example.newsex.presentation.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsex.domain.entity.TopHeadlinesModel
import com.example.newsex.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NewsRxViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private var _topHeadlines = MutableLiveData<List<TopHeadlinesModel>>()
    val topHeadlines: LiveData<List<TopHeadlinesModel>> = _topHeadlines

    init {
        getEverything(COUNTRY_DEFAULT)
    }

    @SuppressLint("CheckResult")
    fun getEverything(country: String) {
       newsUseCase.getEverything(country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _topHeadlines.value = it
                },
                { error ->
                    Log.e(TAG, "Error fetching user: ${error.message}")
                }
            )
    }

    companion object {
        private const val TAG = "NewsRxViewModel"
        private const val COUNTRY_DEFAULT = "US"
    }
}