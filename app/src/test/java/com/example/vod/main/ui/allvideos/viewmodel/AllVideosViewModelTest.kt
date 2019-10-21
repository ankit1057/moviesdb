package com.example.vod.main.ui.allvideos.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.vod.main.utils.TestUtils
import com.example.vod.main.utils.asPagedList
import com.example.vod.main.utils.getOrAwaitValue
import com.example.vod.main.model.MovieModel
import com.example.vod.main.ui.allvideos.repository.MoviesDataFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AllVideosViewModelTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var viewModel: AllVideosViewModel
    private lateinit var pagedKeyInitial: PageKeyedDataSource.LoadInitialParams<Int>
    private lateinit var loadCallBackInitial: PageKeyedDataSource.LoadInitialCallback<Int, MovieModel>

    @Mock
    private lateinit var dataFactory: MoviesDataFactory
    @Mock
    private lateinit var config: PagedList.Config


    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        pagedKeyInitial = TestUtils.getInitialParams()
        loadCallBackInitial = TestUtils.getInitialLoadCallBack()
    }

    @Test
    fun `on error exists error live data value not equal null`() {


        viewModel = AllVideosViewModel(config, dataFactory)
        viewModel.moviesResponse = MutableLiveData<PagedList<MovieModel?>>().apply {
            this.value = TestUtils.constructActualMockedResponse().results?.asPagedList(
                    PagedList.Config.Builder().setEnablePlaceholders(false)
                            .setInitialLoadSizeHint(20)
                            .setPageSize(20)
                            .build())
        }
        Assert.assertNotNull(viewModel.moviesResponse.value)
        Assert.assertEquals(viewModel.moviesResponse.getOrAwaitValue()[0]?.original_title, "money heist")
        Assert.assertEquals(viewModel.moviesResponse.getOrAwaitValue().loadedCount, 3)

    }


}