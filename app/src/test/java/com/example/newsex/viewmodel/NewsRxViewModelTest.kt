package com.example.newsex.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newsex.domain.entity.TopHeadlinesModel
import com.example.newsex.domain.usecase.NewsUseCase
import com.example.newsex.presentation.viewmodel.NewsRxViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRxViewModelTest {

    @Rule
    @JvmField
    var instantDelayedTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var newsUseCase: NewsUseCase

    @MockK
    private lateinit var topHeadlinesObserver: Observer<List<TopHeadlinesModel>>

    private lateinit var newsViewModel: NewsRxViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        newsViewModel = NewsRxViewModel(newsUseCase)
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
        // Consider a hypothetical situation involving getEverything()
        every { newsUseCase.getEverything(any()) }.returns(Single.just(topHeadlinesModels))

        newsViewModel = NewsRxViewModel(newsUseCase)
        newsViewModel.topHeadlines.observeForever(topHeadlinesObserver)

        // topHeadlinesObserver is changed one time with any data. You can specify specific data for it.
        verify(exactly = 1) { topHeadlinesObserver.onChanged(any()) }
        assertTrue(newsViewModel.topHeadlines.value?.size == 1)
    }

    @Test
    fun `test getTopHeadlines - on error`() {
        every { newsUseCase.getEverything(any()) }.returns(Single.error(Exception("Bad request")))

        newsViewModel = NewsRxViewModel(newsUseCase)
        newsViewModel.topHeadlines.observeForever(topHeadlinesObserver)

        verify(inverse = true) { topHeadlinesObserver.onChanged(any()) }
        assertNull(newsViewModel.topHeadlines.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}