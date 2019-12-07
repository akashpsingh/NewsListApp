package com.example.newslist.ui.fragment.newsdetail

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.newslist.R
import com.example.newslist.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_news_web_view.*

class NewsWebViewFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_news_web_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url =
            arguments?.getString(EXTRA_URL) ?: throw NullPointerException("url cannot be null")
        setupWebView(url)
        srlRefresh.setOnRefreshListener {
            webView.loadUrl(url)
        }
    }

    private fun setupWebView(url: String) {
        webView.apply {
            settings.apply {
                javaScriptEnabled = true
            }
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    srlRefresh.isRefreshing = true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    srlRefresh.isRefreshing = false
                }
            }
            loadUrl(url)
        }
    }

    override fun onDestroyView() {
        webView.webViewClient = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "NewsWebViewFragment"
        private const val EXTRA_URL = "url"

        fun newInstance(url: String): NewsWebViewFragment {
            return NewsWebViewFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(EXTRA_URL, url)
                }
            }
        }
    }
}