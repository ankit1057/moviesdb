package com.example.vod.main.ui.allvideos.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.vod.main.model.ErrorModel
import com.example.vod.main.model.MovieModel
import com.example.vod.main.ui.allvideos.repository.MoviesDataFactory
import org.koin.core.KoinComponent


class AllVideosViewModel(config: PagedList.Config, var moviesDataFactory: MoviesDataFactory) : ViewModel(), KoinComponent {

    var moviesResponse: LiveData<PagedList<MovieModel?>>
    var onErrorOccurred: LiveData<ErrorModel> = MutableLiveData()

    init {
        onErrorOccurred = Transformations.switchMap(moviesDataFactory.mutableLiveData
        ) { dataSource -> dataSource.getErrorLiveData() }
        moviesResponse = initializedPagedListBuilder(config).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, MovieModel> {
        return LivePagedListBuilder(moviesDataFactory, config)
    }

}


