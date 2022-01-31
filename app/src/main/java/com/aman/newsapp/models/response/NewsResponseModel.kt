package com.aman.newsapp.models.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NewsResponseModel(
    @SerializedName("articles") val articles: List<Article>,
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int
)