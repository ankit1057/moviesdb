package com.example.vod.main.model

import java.io.Serializable


data class MovieModel(var popularity: Float, var vote_count: Int, var id: Int,
                      var original_language: String?, var original_title: String?,
                      var overview: String?, var poster_path: String?, var release_date: String?):Serializable



