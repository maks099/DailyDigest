package com.daily.digest

import android.app.Application
import com.daily.digest.di.AppComponent
import com.daily.digest.di.DaggerAppComponent
import com.daily.digest.di.NewsLoaderModule

class DailyDigestApp : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .newsLoaderModule(NewsLoaderModule(this))
            .build()
    }
}