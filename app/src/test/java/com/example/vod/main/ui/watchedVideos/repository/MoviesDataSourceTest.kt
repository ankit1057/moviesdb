package com.example.vod.main.ui.watchedVideos.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PageKeyedDataSource
import com.example.vod.main.utils.TestUtils
import com.example.vod.main.model.MovieModel
import com.example.vod.main.networking.ApiService
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.Mockito
import retrofit2.Response


@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class MoviesDataSourceTest : KoinTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var pagedKeyInitial: PageKeyedDataSource.LoadInitialParams<Int>
    private lateinit var loadCallBackInitial: PageKeyedDataSource.LoadInitialCallback<Int, MovieModel>


    @Before
    fun setUp() {
        pagedKeyInitial = TestUtils.getInitialParams()
        loadCallBackInitial = TestUtils.getInitialLoadCallBack()
    }

    @Test
    fun `test on Success Api `() {
        val mockResponse = TestUtils.constructActualMockedResponse()

        val service = mockk<ApiService>()
        every { runBlocking { service.allVideosAsync(any(), any(), any(), any()) } } returns CompletableDeferred(
                Response.success(
                        mockResponse
                )
        )
        val moviesDataSource = MoviesDataSource(Mockito.mock(Context::class.java), service, CoroutineScope(Dispatchers.Unconfined))
        Assert.assertNull(moviesDataSource.getErrorLiveData().value)

    }

    @Test
    fun `test on failure Api when error body is not empty`() {

        val service = mockk<ApiService>()
        every { runBlocking { service.allVideosAsync(any(), any(), any(), any()) } } returns CompletableDeferred(
                Response.error(
                        401, TestUtils.constructErrorBody()
                )
        )
        val moviesDataSource = MoviesDataSource(Mockito.mock(Context::class.java), service, CoroutineScope(Dispatchers.Unconfined))
        moviesDataSource.loadInitial(pagedKeyInitial, loadCallBackInitial)
        Assert.assertNotNull(moviesDataSource.getErrorLiveData().value)
        Assert.assertEquals(moviesDataSource.getErrorLiveData().value?.status_code, 7)
    }

    @Test
    fun `test on failure Api throwing exception`() {

        val service = mockk<ApiService>()
        every { runBlocking { service.allVideosAsync(any(), any(), any(), any()) } } throws (IllegalStateException())
        val moviesDataSource = MoviesDataSource(Mockito.mock(Context::class.java), service, CoroutineScope(Dispatchers.Unconfined))
        moviesDataSource.loadInitial(pagedKeyInitial, loadCallBackInitial)
        Assert.assertNotNull(moviesDataSource.getErrorLiveData().value)
        Assert.assertEquals(moviesDataSource.getErrorLiveData().value?.status_code, 500)
    }


}