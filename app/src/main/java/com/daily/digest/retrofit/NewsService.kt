package com.daily.digest.retrofit

import com.daily.digest.BuildConfig
import com.daily.digest.model.News
import com.daily.digest.model.NewsResponse
import retrofit2.http.GET

interface NewsService {

    @GET("/v2/top-headlines?country=ua&apiKey=${BuildConfig.NEWS_API_KEY}")
    suspend fun getNews(): Result<NewsResponse>
}