package com.daily.digest.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.daily.digest.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    fun getAll(): Flow<List<News>>

    @Insert
    suspend fun insertAll(vararg news: News)

    @Delete
    fun delete(news: News)

}