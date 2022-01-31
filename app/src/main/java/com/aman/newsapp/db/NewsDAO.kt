package com.aman.newsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aman.newsapp.models.response.Article
import com.aman.newsapp.models.response.SourceDetail

@Dao
interface NewsDAO {

    @Insert
    suspend fun addTopHeadlines(articles: List<Article>)

    @Insert
    suspend fun addSources(sources: List<SourceDetail>)

    @Query("SELECT * FROM news_table")
    suspend fun getTopHeadlines() : List<Article>

    @Query("SELECT * FROM source_table")
    suspend fun getSources() : List<SourceDetail>
}