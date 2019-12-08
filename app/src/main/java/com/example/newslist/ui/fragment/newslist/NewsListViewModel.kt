package com.example.newslist.ui.fragment.newslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newslist.model.Article
import com.example.newslist.repository.BaseRepository
import com.example.newslist.repository.NewsListRepository
import com.example.newslist.ui.BaseViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel(private val newsListRepository: BaseRepository<List<Article>>) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val newsListViewState = MutableLiveData<NewsListViewState>()

    class Factory @Inject constructor(private val newsListRepository: NewsListRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsListViewModel(
                newsListRepository
            ) as T
        }
    }

    fun fetchTopHeadlines() {
        val currentViewState = newsListViewState.value?.currentViewState
        if (currentViewState == BaseViewState.ViewState.SUCCESS) {
            return
        }
        newsListViewState.postValue(
            NewsListViewState(null, BaseViewState.ViewState.LOADING)
        )
        compositeDisposable.add(
            newsListRepository.getData()
                .map { convertToViewModel(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(it)
                }, {
                    onError(it)
                })
        )
    }

    private fun onSuccess(newsList: List<NewsArticle>) {
        newsListViewState.postValue(
            NewsListViewState(newsList, BaseViewState.ViewState.SUCCESS)
        )
    }

    private fun onError(error: Throwable) {
        newsListViewState.postValue(
            NewsListViewState(null, BaseViewState.ViewState.ERROR, error)
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