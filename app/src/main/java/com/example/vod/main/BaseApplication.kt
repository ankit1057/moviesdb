package com.example.vod.main

import android.app.Application
import com.example.vod.main.di.applicationModule
import com.example.vod.main.di.networkModule
import com.example.vod.main.di.repositoriesModules
import com.example.vod.main.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplication)
            androidFileProperties()
            modules(listOf(applicationModule, networkModule, viewModelModule, repositoriesModules
            ))
        }
    }
}