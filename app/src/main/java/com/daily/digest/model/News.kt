package com.daily.digest.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String?,
    @SerializedName("author")
    val author: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("urlToImage")
    val urlToImage: String? = null
)