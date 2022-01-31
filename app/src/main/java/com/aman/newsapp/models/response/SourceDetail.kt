package com.aman.newsapp.models.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "source_table")
data class SourceDetail(
    @PrimaryKey(autoGenerate = true) val sourceId: Int,
    @SerializedName("category") val category: String,
    @SerializedName("country") val country: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)