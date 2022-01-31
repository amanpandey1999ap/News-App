package com.aman.newsapp.models.response

import com.google.gson.annotations.SerializedName

data class NewsSourceResponseModel(
    @SerializedName("sources") val sources: List<SourceDetail>,
    @SerializedName("status") val status: String
)
