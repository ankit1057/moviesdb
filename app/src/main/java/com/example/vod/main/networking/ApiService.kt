package com.example.vod.main.networking

import com.example.vod.main.model.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?")
   open fun allVideosAsync(@Query("api_key") apiKey: String,
                       @Query("language") language: String,
                       @Query("sort_by") sortBy: String, @Query("page") page: Int)
            : Deferred<Response<MoviesResponse?>>
}