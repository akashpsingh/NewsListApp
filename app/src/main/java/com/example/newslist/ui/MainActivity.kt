package com.example.newslist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newslist.R
import com.example.newslist.ui.fragment.NewsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentByTag(NewsListFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, NewsListFragment(), NewsListFragment.TAG)
                .commit()
        }
    }
}
