package com.example.newslist.network

import com.example.newslist.model.NewsList
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines?country=us")
    fun getTopHeadlines(): Single<NewsList>
}