package com.example.newslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newslist.repository.NewsListRepository
import com.example.newslist.ui.BaseViewState
import com.example.newslist.ui.fragment.newslist.NewsListViewModel
import com.example.newslist.ui.fragment.newslist.NewsListViewState
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class NewsListViewModelTest {

    private lateinit var vm: NewsListViewModel
    @Mock
    lateinit var newsListRepository: NewsListRepository
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()
    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()
    @Mock
    lateinit var mockLiveDataObserver: Observer<NewsListViewState>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        vm = NewsListViewModel(newsListRepository)
        vm.newsListViewState.observeForever(mockLiveDataObserver)
    }

    @Test
    fun testApiDataSuccess() {
        `when`(newsListRepository.getData()).thenReturn(Single.just(emptyList()))
        vm.fetchTopHeadlines()
        Assert.assertEquals(
            vm.newsListViewState.value?.currentViewState,
            BaseViewState.ViewState.SUCCESS
        )
        Assert.assertNotNull(vm.newsListViewState.value?.data)
    }

    @Test
    fun testApiError() {
        `when`(newsListRepository.getData()).thenReturn(Single.error(Throwable()))
        vm.fetchTopHeadlines()
        Assert.assertEquals(
            vm.newsListViewState.value?.currentViewState,
            BaseViewState.ViewState.ERROR
        )
        Assert.assertNull(vm.newsListViewState.value?.data)
    }

    @Test
    fun testObserverInvokedOnLiveDataChange() {
        `when`(newsListRepository.getData()).thenReturn(Single.just(emptyList()))
        vm.fetchTopHeadlines()
        verify(mockLiveDataObserver, times(2)).onChanged(any())
    }

    @After
    fun tearDown() {
    }
}