package com.example.newslist

import com.example.newslist.db.NewsDao
import com.example.newslist.model.Article
import com.example.newslist.model.NewsList
import com.example.newslist.network.NewsService
import com.example.newslist.repository.NewsListRepository
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import org.mockito.Mockito.`when`

class NewsListRepositoryTest {

    lateinit var repository: NewsListRepository
    @Mock
    lateinit var newsService: NewsService
    @Mock
    lateinit var newsDbDao: NewsDao
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = NewsListRepository(newsService, newsDbDao)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testOnNetworkSuccess() {
        `when`(newsService.getTopHeadlines()).thenReturn(Single.just(NewsList(listOf(mockNetworkArticle()))))
        `when`(newsDbDao.getTopHeadlines()).thenReturn(Maybe.just(listOf(mockDbArticle())))
        repository.getData()
            .test()
            .assertResult(listOf(mockNetworkArticle()))
    }

    @Test
    fun testOnNetworkErrorDbFetch() {
        `when`(newsService.getTopHeadlines()).thenReturn(Single.error(Throwable()))
        `when`(newsDbDao.getTopHeadlines()).thenReturn(Maybe.just(listOf(mockDbArticle())))
        repository.getData()
            .test()
            .assertResult(listOf(mockDbArticle()))
    }

    private fun mockNetworkArticle(): Article {
        return Article(
            1,
            "network",
            "network",
            "network",
            "http://google.com",
            "http://google.com",
            null,
            null
        )
    }

    private fun mockDbArticle(): Article {
        return Article(
            2,
            "db",
            "db",
            "db",
            "http://google.com",
            "http://google.com",
            null,
            null
        )
    }
}