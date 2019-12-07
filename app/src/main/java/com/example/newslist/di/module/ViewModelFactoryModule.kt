package com.example.newslist.di.module

import com.example.newslist.repository.NewsListRepository
import com.example.newslist.ui.fragment.newslist.NewsListViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun providesNewsListViewModelFactory(newsListRepository: NewsListRepository): NewsListViewModel.Factory {
        return NewsListViewModel.Factory(newsListRepository)
    }

}