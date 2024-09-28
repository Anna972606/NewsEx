package com.example.newsex.domain

import com.example.newsex.TestUtils
import com.example.newsex.api.NewsApi
import com.example.newsex.data.datasource.TopHeadlinesResponse
import com.example.newsex.data.repository.NewsRepository
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.mockk.MockKAnnotations
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsRepositoryTest {

    private val mockWebServer = MockWebServer()
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        newsRepository = NewsRepository(NewsApi(retrofit))
    }

    @Test
    fun `test getTopHeadlines on success`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(TestUtils.getJsonFromResource("top_head_lines_response.json"))
        )

        val result = newsRepository.getTopHeadlines("us").first()

        assertTrue(result?.articles?.size == 2)
    }

    @Test
    fun `test getTopHeadlines on error`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("Bad request"))

        val result = newsRepository.getTopHeadlines("us").first()

        assertNull(result)
    }

    @Test
    fun `test getEverything on success`() {
        val json = TestUtils.getJsonFromResource("top_head_lines_response.json")
        val response = Gson().fromJson(
            json,
            TopHeadlinesResponse::class.java
        )
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(json))

        val result = newsRepository.getEverything("us").test()

        result.assertValue(response)
    }

    @Test
    fun `test getEverything on error`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("Bad request"))

        val result = newsRepository.getEverything("us").test()

        result.assertNoValues()
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}