package com.example.newslist.ui.fragment.newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newslist.R
import com.example.newslist.model.Article
import kotlinx.android.synthetic.main.recycler_item_news.view.*

class NewsRecyclerAdapter(
    private val newsArticleClickListener: NewsArticleClickListener
) : RecyclerView.Adapter<NewsRecyclerAdapter.ArticleViewHolder>() {

    private val newsList = mutableListOf<NewsListViewModel.NewsArticle>()

    fun updateNewsArticles(newsList: List<NewsListViewModel.NewsArticle>) {
        this.newsList.clear()
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item_news,
            parent,
            false
        )
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val articleImage: ImageView = itemView.articleImage
        private val articleDescription: TextView = itemView.articleDescriptionText
        private val articleTitle: TextView = itemView.articleTitleText

        fun bind(article: NewsListViewModel.NewsArticle) {
            Glide.with(articleImage)
                .load(article.imageUrl)
                .placeholder(R.drawable.img_placeholder)
                .centerCrop()
                .into(articleImage)

            articleDescription.text = article.description
            articleTitle.text = article.title

            itemView.setOnClickListener {
                newsArticleClickListener.onArticleClicked(article.url)
            }
        }
    }

    interface NewsArticleClickListener {
        fun onArticleClicked(url: String)
    }
}