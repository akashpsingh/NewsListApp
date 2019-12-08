package com.example.newslist.ui.fragment.newslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newslist.model.Article
import com.example.newslist.repository.BaseRepository
import com.example.newslist.repository.NewsListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel(private val newsListRepository: BaseRepository<List<Article>>) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    var topHeadlinesLiveData = MutableLiveData<List<NewsArticle>>()
    val errorLiveData = MutableLiveData(false)
    val showLoaderLiveData = MutableLiveData(true)

    class Factory @Inject constructor(private val newsListRepository: NewsListRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsListViewModel(
                newsListRepository
            ) as T
        }
    }

    fun fetchTopHeadlines() {
        if (topHeadlinesLiveData.value?.isEmpty() == false) {
            return
        }
        errorLiveData.value = false
        showLoaderLiveData.value = true

        compositeDisposable.add(
            newsListRepository.getData()
                .map { convertToViewModel(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    topHeadlinesLiveData.value = it
                    errorLiveData.value = false
                    showLoaderLiveData.value = false
                }, {
                    errorLiveData.value = true
                    showLoaderLiveData.value = false
                })
        )
    }

    private fun convertToViewModel(articleDMList: List<Article>): List<NewsArticle> {
        return articleDMList.map {
            NewsArticle(
                it.title,
                it.description,
                it.url,
                it.imageUrl
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    data class NewsArticle(
        val title: String,
        val description: String?,
        val url: String,
        val imageUrl: String?
    )
}