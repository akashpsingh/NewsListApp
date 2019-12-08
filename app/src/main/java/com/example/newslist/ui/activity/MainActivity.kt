package com.example.newslist.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newslist.R
import com.example.newslist.ui.fragment.newslist.NewsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsListFragment = supportFragmentManager.findFragmentByTag(NewsListFragment.TAG)
        if (newsListFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, NewsListFragment(), NewsListFragment.TAG)
                .commit()
        }
    }
}
