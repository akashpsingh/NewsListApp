package com.example.newslist.di.module

import com.example.newslist.db.AppDatabase
import com.example.newslist.network.NewsService
import com.example.newslist.repository.NewsListRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesNewsListRepository(
        newsService: NewsService,
        appDatabase: AppDatabase
    ) = NewsListRepository(newsService, appDatabase.userDao())
}