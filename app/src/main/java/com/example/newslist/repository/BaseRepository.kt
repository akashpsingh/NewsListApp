package com.example.newslist.repository

import io.reactivex.Single

interface BaseRepository<T> {
    fun getData(): Single<T>
}