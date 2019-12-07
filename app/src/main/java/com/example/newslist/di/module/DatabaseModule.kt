package com.example.newslist.di.module

import android.content.Context
import androidx.room.Room
import com.example.newslist.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesRoomDb(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "news-db"
        ).build()
}