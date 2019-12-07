package com.example.newslist.di.module

import com.example.newslist.network.NewsService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesNewsService(retrofit: Retrofit): NewsService = retrofit.create(NewsService::class.java)

    @Provides
    @Singleton
    fun providesRetrofit(@Named(NAME_BASE_URL) baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesOkHttpClient(@Named(NAME_API_KEY) apiKey: String): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                val newUrl = request.url().newBuilder().addQueryParameter("apiKey", apiKey).build()
                val newRequest = request.newBuilder().url(newUrl).build()
                it.proceed(newRequest)
            }
            .build()

    @Provides
    @Named(NAME_BASE_URL)
    fun providesBaseUrl() = "https://newsapi.org/v2/"

    @Provides
    @Named(NAME_API_KEY)
    fun providesApiKey() = "87af6baf613c4a16b11ffdc169238631"

    companion object {
        private const val NAME_BASE_URL = "BASE_URL"
        private const val NAME_API_KEY = "API_KEY"
        private const val API_KEY = "BASE_URL"
    }
}