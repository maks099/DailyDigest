package com.daily.digest.retrofit

import com.daily.digest.News
import retrofit2.http.GET

interface NewsService {

    @GET("/news")
    suspend fun getNews(): Result<List<News>>
}