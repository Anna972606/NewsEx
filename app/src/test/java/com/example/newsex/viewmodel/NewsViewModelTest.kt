package com.example.newsex.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newsex.domain.entity.TopHeadlinesModel
import com.example.newsex.domain.usecase.NewsUseCase
import com.example.newsex.presentation.viewmodel.NewsViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    @Rule
    @JvmField
    var instantDelayedTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var newsUseCase: NewsUseCase

    @MockK
    private lateinit var topHeadlinesObserver: Observer<List<TopHeadlinesModel>>

    private lateinit var newsViewModel: NewsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        newsViewModel = NewsViewModel(newsUseCase)
    }

    @Test
    fun `test getTopHeadlines - on success`() {
        val topHeadlinesModels = listOf(
            TopHeadlinesModel(
                author = "",
                content = "",
                description = "",
                publishedAt = "",
                title = "",
                url = "",
                urlToImage = "",
                id = "",
                name = ""
            )
        )

        every { newsUseCase.getTopHeadlines(any()) }.returns(flowOf(topHeadlinesModels))

        newsViewModel = NewsViewModel(newsUseCase)
        newsViewModel.topHeadlines.observeForever(topHeadlinesObserver)

        assertTrue(newsViewModel.topHeadlines.value?.size == 1)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}