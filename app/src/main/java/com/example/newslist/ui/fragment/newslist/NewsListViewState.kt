package com.example.newslist.ui.fragment.newslist

import com.example.newslist.ui.BaseViewState

class NewsListViewState(
    data: List<NewsListViewModel.NewsArticle>?,
    currentState: ViewState,
    error: Throwable? = null
) : BaseViewState<List<NewsListViewModel.NewsArticle>>(
    data,
    currentState,
    error
)