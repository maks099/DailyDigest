package com.daily.digest.di

import com.daily.digest.MainActivity
import com.daily.digest.retrofit.NewsService
import dagger.Component

@Component(modules = [NewsLoaderModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}