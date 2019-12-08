package com.example.newslist.ui.fragment.newslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newslist.R
import com.example.newslist.di.DIHelper.appComponent
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
    }

    private fun createViewModel() {
        vm = ViewModelProviders.of(this, vmFactory)[NewsListViewModel::class.java]
        vm.topHeadlinesLiveData.observe(this, Observer {
            newsRecyclerAdapter.updateNewsArticles(it)
        })
        vm.errorLiveData.observe(this, Observer { show ->
            errorLayout.visibility = if (show) View.VISIBLE else View.GONE
            tryAgainBtn.setOnClickListener {
                vm.fetchTopHeadlines()
            }
        })
        vm.showLoaderLiveData.observe(this, Observer { show ->
            progressBar.visibility = if (show) View.VISIBLE else View.GONE
        })
        vm.fetchTopHeadlines()
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