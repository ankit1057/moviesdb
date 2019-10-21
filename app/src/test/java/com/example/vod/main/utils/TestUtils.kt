package com.example.vod.main.utils

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.vod.main.model.MovieModel
import com.example.vod.main.model.MoviesResponse
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.mockito.Mockito

class TestUtils {

    companion object {


        fun constructActualMockedResponse(): MoviesResponse {
            val results = ArrayList<MovieModel>()
            results.add(MovieModel(9.5f, 5, 0, "en",
                    "money heist", "", "", ""))
            results.add(MovieModel(9.5f, 5, 0, "en",
                    "focus", "", "", ""))
            results.add(MovieModel(9.5f, 5, 0, "en",
                    "joker", "", "", ""))
            val response = MoviesResponse()
            response.page = 1
            response.totalPages = 5
            response.results = results
            return response
        }

        fun constructErrorBody(): ResponseBody {
            return ResponseBody.create(MediaType.parse("application/json"), "{\n" +
                    "  \"status_code\": 7,\n" +
                    "  \"status_message\": \"Invalid API key: You must be granted a valid key.\",\n" +
                    "  \"success\": false\n" +
                    "}")
        }

        fun providePageListConfig(): PagedList.Config =
                PagedList.Config.Builder()
                        .setPageSize(20)
                        .setEnablePlaceholders(false)
                        .build()

        fun getInitialParams(): PageKeyedDataSource.LoadInitialParams<Int> {
            return Mockito.mock(PageKeyedDataSource.LoadInitialParams::class.java)
                    as PageKeyedDataSource.LoadInitialParams<Int>
        }

        fun getInitialLoadCallBack(): PageKeyedDataSource.LoadInitialCallback<Int, MovieModel> {
            return Mockito.mock(PageKeyedDataSource.LoadInitialCallback::class.java)
                    as PageKeyedDataSource.LoadInitialCallback<Int, MovieModel>
        }
    }

}