package com.daily.digest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daily.digest.model.News

@Database(entities = [News::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
