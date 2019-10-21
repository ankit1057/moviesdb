package com.example.vod.main.ui.watchedVideos.repository

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.vod.R
import com.example.vod.main.model.ErrorModel
import com.example.vod.main.model.MovieModel
import com.example.vod.main.model.MoviesResponse
import com.example.vod.main.networking.ApiService
import com.example.vod.main.utils.NetworkConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import retrofit2.Response

class MoviesDataSource(var context: Context, private var apiService: ApiService, private var coroutineScope: CoroutineScope) : PageKeyedDataSource<Int, MovieModel>(), KoinComponent {

    private lateinit var response: Response<MoviesResponse?>
    private var onErrorOccurred: MutableLiveData<ErrorModel> = MutableLiveData()

    private var totalPages = 0
    private var currentPageCount = 0



    fun getErrorLiveData(): MutableLiveData<ErrorModel> {
        return onErrorOccurred
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieModel>) {
        coroutineScope.launch {
            runCatching {
                currentPageCount = 1
                response = apiService.allVideosAsync(NetworkConstants.REQUEST_API_KEY,
                        NetworkConstants.REQUEST_LANGUAGE_KEY, NetworkConstants.REQUEST_SORTBY_KEY,
                        1).await()
            }.onSuccess {
                parseInitialResponse(response, callback)
            }
                    .onFailure {
                        constructErrorModel(null)
                    }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        if (params.key <= totalPages) {
            coroutineScope.launch {
                runCatching {
                    currentPageCount = params.key
                    response = apiService.allVideosAsync(NetworkConstants.REQUEST_API_KEY,
                            NetworkConstants.REQUEST_LANGUAGE_KEY, NetworkConstants.REQUEST_SORTBY_KEY,
                            currentPageCount).await()

                }.onSuccess {
                    parseLoadAfterResponse(response, callback)
                }
                        .onFailure {
                            constructErrorModel(null)
                        }
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
    }

    private fun parseInitialResponse(response: Response<MoviesResponse?>, callback: LoadInitialCallback<Int, MovieModel>) {
        if (response.isSuccessful) {
            val movieResponse = response.body()
            totalPages = movieResponse?.totalPages!!
            val nextPageKey = movieResponse.page?.plus(1)
            movieResponse.results?.let { callback.onResult(it, null, nextPageKey) }
        } else {
            constructErrorModel(response)
        }
    }

    private fun parseLoadAfterResponse(response: Response<MoviesResponse?>, callback: LoadCallback<Int, MovieModel>) {
        if (response.isSuccessful) {
            val movieResponse = response.body()
            totalPages = movieResponse?.totalPages!!
            val nextPageKey = movieResponse.page?.plus(1)
            movieResponse.results?.let { callback.onResult(it, nextPageKey) }
        } else {
            constructErrorModel(response)
        }
    }

    private fun constructErrorModel(response: Response<MoviesResponse?>?) {
        if (response?.errorBody() != null) {
            val type = object : TypeToken<ErrorModel>() {}.type
            onErrorOccurred.postValue(Gson().fromJson(response.errorBody()!!.charStream(), type))
        } else {
            onErrorOccurred.postValue(ErrorModel(500, context.getString(R.string.error_no_internet_connection)))
        }
    }

    override fun invalidate() {
        super.invalidate()
        coroutineScope.cancel()
    }
}
