package com.example.vod.main.repository

import com.example.vod.main.networking.ApiService
import org.koin.core.KoinComponent
import org.koin.core.inject


open class BaseRepository : KoinComponent {
    // TODO: Implement the ViewModel
    protected val apiService: ApiService by inject()
}
