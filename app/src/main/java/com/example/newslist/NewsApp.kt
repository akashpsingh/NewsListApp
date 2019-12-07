package com.example.newslist

import android.app.Application
import com.example.newslist.di.DIHelper

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DIHelper.initDagger(this)
    }
}