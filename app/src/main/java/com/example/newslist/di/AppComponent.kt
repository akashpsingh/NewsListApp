package com.example.newslist.di

import com.example.newslist.di.module.*
import com.example.newslist.ui.activity.MainActivity
import com.example.newslist.ui.fragment.newslist.NewsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(newsListFragment: NewsListFragment)
}