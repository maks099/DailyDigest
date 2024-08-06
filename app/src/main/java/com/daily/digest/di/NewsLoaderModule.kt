package com.daily.digest.di

import android.content.Context
import androidx.room.Room
import com.daily.digest.retrofit.NewsService
import com.daily.digest.room.AppDatabase
import com.daily.digest.room.NewsDao
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NewsLoaderModule(private val appContext: Context) {

    @Provides
    fun provideContext() = appContext

    @Provides
    fun getNewsService(): NewsService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit.create(NewsService::class.java)
    }

    @Provides
    fun getNewsDao(): NewsDao {
        val db = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "database-name"
        ).build()
        return db.newsDao()
    }
}