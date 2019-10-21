package com.example.vod.main.utils

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList

fun <T> ArrayList<T>.asPagedList(config: PagedList.Config? = null): PagedList<T?>? {
    val defaultConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(size)
            .setMaxSize(size + 2)
            .setPrefetchDistance(1)
            .build()
    return LivePagedListBuilder<Int, T>(
            createMockDataSourceFactory(this),
            config ?: defaultConfig
    ).build().getOrAwaitValue()
}

private fun <T> createMockDataSourceFactory(itemList: List<T>): DataSource.Factory<Int, T> =
        object : DataSource.Factory<Int, T>() {
            override fun create(): DataSource<Int, T> = MockDataSource(itemList)
        }




class MockDataSource<T>(private val itemList: List<T>) : PageKeyedDataSource<Int, T>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        callback.onResult(itemList.toMutableList(),null, 0)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        callback.onResult(itemList.toMutableList(), params.key+1)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}