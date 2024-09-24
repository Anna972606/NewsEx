package com.example.newsex.di

import com.example.newsex.api.CacheInterceptor
import com.example.newsex.api.NewsApi
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkhttpClientBuilder(): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            connectTimeout(50L, TimeUnit.SECONDS)
            readTimeout(50L, TimeUnit.SECONDS)
            writeTimeout(50L, TimeUnit.SECONDS)
        }

    @Provides
    @Named("default")
    fun provideInterceptors(): InterceptorList {
        val interceptors = listOf(
            CacheInterceptor(),
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        return InterceptorList(interceptors)
    }

    @Provides
    @Named("default")
    fun provideOkHttpClient(
        builder: OkHttpClient.Builder,
        @Named("default") interceptorContainer: InterceptorList,
    ): OkHttpClient = builder.apply {
        interceptorContainer.interceptors.forEach { addInterceptor(it) }
    }.build()

    @Provides
    fun provideRetrofitBuilder(
        @Named("default") okHttpClient: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit.Builder) = NewsApi(retrofit)

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
    }
}

data class InterceptorList(val interceptors: List<Interceptor>)