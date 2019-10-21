package com.example.vod.main.di

import androidx.paging.PagedList
import androidx.paging.PagedList.Config
import androidx.recyclerview.widget.DiffUtil
import com.example.vod.main.model.MovieModel
import com.example.vod.main.networking.ApiService
import com.example.vod.main.ui.allvideos.repository.MoviesDataFactory
import com.example.vod.main.ui.allvideos.viewmodel.AllVideosViewModel
import com.example.vod.main.ui.allvideos.views.VideosAdapter
import com.example.vod.main.ui.videodetails.viewmodel.VideoDetailsViewModel
import com.example.vod.main.ui.watchedVideos.repository.MoviesDataSource
import com.example.vod.main.utils.DateHelper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module {
    single { DateHelper() }
    factory { VideosAdapter(provideDiffUtilCallBack()) }
    factory { MoviesDataFactory(get()) }

}

val networkModule = module {
    single { provideRetrofit().create(ApiService::class.java) }
}

val viewModelModule = module {
    viewModel { AllVideosViewModel(providePageListConfig(), get()) }
    viewModel { VideoDetailsViewModel() }
}

val repositoriesModules = module {
    factory { MoviesDataSource(androidApplication(), get(), CoroutineScope(Dispatchers.IO)) }

}



fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
}

fun provideDiffUtilCallBack(): DiffUtil.ItemCallback<MovieModel> {
    return object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }
}

fun providePageListConfig(): Config =
        Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build()



