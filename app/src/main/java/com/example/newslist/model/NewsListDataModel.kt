package com.example.newslist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newslist.db.NewsDao.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("articles") val articles: List<Article>
)

@Entity(tableName = TABLE_NAME)
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("content") val content: String?
)