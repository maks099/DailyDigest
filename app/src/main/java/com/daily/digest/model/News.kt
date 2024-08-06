package com.daily.digest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("title")
    val title: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("urlToImage")
    val urlToImage: String? = null
)