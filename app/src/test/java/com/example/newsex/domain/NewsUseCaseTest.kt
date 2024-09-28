package com.example.newsex.domain

import com.example.newsex.TestUtils
import com.example.newsex.data.datasource.TopHeadlinesResponse
import com.example.newsex.data.mapper.NewsMapper
import com.example.newsex.data.repository.NewsProvider
import com.example.newsex.domain.entity.TopHeadlinesModel
import com.example.newsex.domain.usecase.NewsUseCase
import com.example.newsex.domain.usecase.NewsUseCaseImpl
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NewsUseCaseTest {

    @RelaxedMockK
    private lateinit var newsProvider: NewsProvider

    @RelaxedMockK
    private lateinit var newsMapper: NewsMapper

    private lateinit var newsUseCase: NewsUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        newsUseCase = NewsUseCaseImpl(newsProvider, newsMapper)
    }

    @Test
    fun `test getTopHeadlines`()  = runBlocking{
        val response = Gson().fromJson(
            TestUtils.getJsonFromResource("top_head_lines_response.json"),
            TopHeadlinesResponse::class.java
        )
        every { newsProvider.getTopHeadlines(any()) }.returns(flowOf(response))
        every { newsMapper.transformTopHeadlinesModel(response) }.returns(
            listOf(
                TopHeadlinesModel(
                    "author",
                    "content",
                    "description",
                    "publishedAt",
                    "title",
                    "url",
                    "urlToImage",
                    "id",
                    "name"
                )
            )
        )

        newsUseCase = NewsUseCaseImpl(newsProvider, newsMapper)
        newsUseCase.getTopHeadlines("").collect{
            Assert.assertNotNull(it)
        }
    }


    @Test
    fun `test getEverything`() {
        val response = Gson().fromJson(
            TestUtils.getJsonFromResource("top_head_lines_response.json"),
            TopHeadlinesResponse::class.java
        )
        val expect = listOf(
            TopHeadlinesModel(
                "author",
                "content",
                "description",
                "publishedAt",
                "title",
                "url",
                "urlToImage",
                "id",
                "name"
            )
        )
        every { newsProvider.getEverything(any()) }.returns(Single.just(response))
        every { newsMapper.transformTopHeadlinesModel(response) }.returns(expect)

        newsUseCase = NewsUseCaseImpl(newsProvider, newsMapper)
        val result = newsUseCase.getEverything("US").test()

        result.assertComplete()
        result.assertNoErrors()
        result.assertValue(expect)
    }
}