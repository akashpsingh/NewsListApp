package com.example.newslist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newslist.model.Article
import com.example.newslist.model.NewsList

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): NewsDao
}