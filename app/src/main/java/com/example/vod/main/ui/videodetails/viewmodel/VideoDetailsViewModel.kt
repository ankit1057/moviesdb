package com.example.vod.main.ui.videodetails.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vod.main.model.MovieModel


class VideoDetailsViewModel : ViewModel() {

    var videoEntityData: MutableLiveData<MovieModel> = MutableLiveData()

    init {
    }

    fun setData(videoEntity: MovieModel) {
        videoEntityData.value = videoEntity
    }


}// TODO: Implement the ViewModel


