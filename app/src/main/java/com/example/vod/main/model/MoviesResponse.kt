package com.example.vod.main.model

import com.google.gson.annotations.SerializedName

class MoviesResponse {

    @SerializedName("results")
    var results: ArrayList<MovieModel>? = null
    @SerializedName("page")
    var page: Int? = null
    @SerializedName("total_pages")
    var totalPages: Int? = null
}
