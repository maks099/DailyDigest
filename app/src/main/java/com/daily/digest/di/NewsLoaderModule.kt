package com.daily.digest.di

import com.daily.digest.retrofit.NewsService
import dagger.Module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NewsLoaderModule {

    fun getNewsService(): NewsService {
        val retrofit: Retrofit =Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NewsService::class.java)
    }
}