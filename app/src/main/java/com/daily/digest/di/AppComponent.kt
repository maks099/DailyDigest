package com.daily.digest.di

import com.daily.digest.MainActivity
import com.daily.digest.retrofit.NewsService
import com.daily.digest.ui.screens.MainScreenViewModel
import dagger.Component

@Component(modules = [NewsLoaderModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(viewModel: MainScreenViewModel)
}