package com.example.newslist.di

import android.app.Application
import com.example.newslist.di.module.AppModule

object DIHelper {

    private lateinit var appComponent: AppComponent

    fun initDagger(app: Application) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
    }

    fun appComponent() = appComponent
}