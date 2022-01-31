package com.aman.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aman.newsapp.models.response.Article
import com.aman.newsapp.models.response.SourceDetail

@Database(entities = [Article::class, SourceDetail::class], version = 4)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDAO

    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        NewsDatabase::class.java,
                        "news_DB"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }
    }
}