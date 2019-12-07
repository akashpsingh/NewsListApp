package com.example.newslist.repository

import android.util.Log
import com.example.newslist.db.NewsDao
import com.example.newslist.model.Article
import com.example.newslist.network.NewsService
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject

class NewsListRepository @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao
) : BaseRepository<List<Article>> {

    override fun getData(): Single<List<Article>> {
        return newsService
            .getTopHeadlines()
            .map {
                Log.v("NewsListRepository", "data fetched from api. saving in db")
                newsDao.deleteAll()
                newsDao.insertAll(it.articles)
                it.articles
            }
            .onErrorResumeNext {
                newsDao.getTopHeadlines().toSingle().map {
                    if (it.isEmpty()) {
                        throw Exception("No news found in db")
                    }
                    it
                }
            }
    }
}