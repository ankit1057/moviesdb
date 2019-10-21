package com.example.vod.main.ui.allvideos.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.vod.main.model.MovieModel
import com.example.vod.main.ui.watchedVideos.repository.MoviesDataSource

class MoviesDataFactory(var moviesDataSource: MoviesDataSource) : DataSource.Factory<Int, MovieModel>() {

    val mutableLiveData: MutableLiveData<MoviesDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, MovieModel> {
        mutableLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }
}