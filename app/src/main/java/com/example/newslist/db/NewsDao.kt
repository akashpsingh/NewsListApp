package com.example.newslist.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newslist.model.Article
import io.reactivex.Maybe

@Dao
interface NewsDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getTopHeadlines(): Maybe<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()

    companion object {
        const val TABLE_NAME = "news"
    }
}