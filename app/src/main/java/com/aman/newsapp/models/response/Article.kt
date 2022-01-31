package com.aman.newsapp.models.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_table")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @SerializedName("author") val author: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?
)
