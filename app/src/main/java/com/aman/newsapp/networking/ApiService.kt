package com.aman.newsapp.networking

import com.aman.newsapp.models.response.NewsResponseModel
import com.aman.newsapp.models.response.NewsSourceResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): Response<NewsResponseModel>

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("sortBy") sortBy: String?
    ): Response<NewsResponseModel>

    @GET("sources")
    suspend fun getSources() : Response<NewsSourceResponseModel>
}