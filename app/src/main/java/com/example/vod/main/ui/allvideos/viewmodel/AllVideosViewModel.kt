package com.example.vod.main.ui.allvideos.viewmodel


import android.util.Log
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.vod.main.model.ErrorModel
import com.example.vod.main.model.MovieModel
import com.example.vod.main.ui.allvideos.repository.MoviesDataFactory
import org.koin.core.KoinComponent


class AllVideosViewModel(config: PagedList.Config, var moviesDataFactory: MoviesDataFactory) : ViewModel(), KoinComponent {
    var sortedMoviesResponse=MediatorLiveData<PagedList<MovieModel>>()
    var moviesResponse: LiveData<PagedList<MovieModel>>
    var onErrorOccurred: LiveData<ErrorModel> = MutableLiveData()

    init {

        onErrorOccurred = Transformations.switchMap(moviesDataFactory.mutableLiveData
        ) { dataSource -> dataSource.getErrorLiveData() }
        moviesResponse = initializedPagedListBuilder(config).build()
        sortedMoviesResponse.addSource(moviesResponse){
            sortedMoviesResponse.value=sortPageList(it)
        }
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, MovieModel> {
        return LivePagedListBuilder(moviesDataFactory, config)
    }

    private fun sortPageList(pagedList: PagedList<MovieModel>): PagedList<MovieModel>
    {
        return pagedList.sorted() as PagedList<MovieModel>
    }
}


