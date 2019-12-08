package com.example.newslist.ui.fragment.newslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newslist.R
import com.example.newslist.di.DIHelper.appComponent
import com.example.newslist.ui.BaseViewState
import com.example.newslist.ui.fragment.BaseFragment
import com.example.newslist.ui.fragment.newsdetail.NewsWebViewFragment
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject

class NewsListFragment : BaseFragment(), NewsRecyclerAdapter.NewsArticleClickListener {

    @Inject
    lateinit var vmFactory: NewsListViewModel.Factory
    lateinit var newsRecyclerAdapter: NewsRecyclerAdapter
    private lateinit var vm: NewsListViewModel

    override fun getLayoutId() = R.layout.fragment_news_list

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        createViewModel()
        tryAgainBtn.setOnClickListener {
            vm.fetchTopHeadlines()
        }
    }

    private fun createViewModel() {
        vm = ViewModelProviders.of(this, vmFactory)[NewsListViewModel::class.java]
        vm.newsListViewState.observe(this, Observer {
            setViewState(it)
        })
        vm.fetchTopHeadlines()
    }

    private fun setViewState(viewState: NewsListViewState) {
        when (viewState.currentViewState) {
            BaseViewState.ViewState.LOADING -> {
                progressBar.visibility = View.VISIBLE
                errorLayout.visibility = View.GONE
            }
            BaseViewState.ViewState.SUCCESS -> {
                newsRecyclerAdapter.updateNewsArticles(viewState.data)
                newsRecyclerView.visibility = View.VISIBLE
                errorLayout.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
            BaseViewState.ViewState.ERROR -> {
                errorLayout.visibility = View.VISIBLE
                newsRecyclerView.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView() {
        newsRecyclerAdapter =
            NewsRecyclerAdapter(this)
        newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsRecyclerAdapter
        }
    }

    override fun onArticleClicked(url: String) {
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    NewsWebViewFragment.newInstance(
                        url
                    ),
                    NewsWebViewFragment.TAG
                )
                .addToBackStack(NewsWebViewFragment.TAG)
                .commit()
        }
    }

    companion object {
        const val TAG = "NewsListFragment"
    }
}