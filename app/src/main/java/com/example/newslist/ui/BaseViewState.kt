package com.example.newslist.ui

open class BaseViewState<T>(
    val data: T?,
    val currentViewState: ViewState,
    val error: Throwable?
) {
    enum class ViewState {
        LOADING,
        ERROR,
        SUCCESS
    }
}