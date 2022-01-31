package com.aman.newsapp.ui.home.repository

import com.aman.newsapp.db.NewsDatabase
import com.aman.newsapp.models.response.Article
import com.aman.newsapp.models.response.SourceDetail
import com.aman.newsapp.networking.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService, private val newsDatabase: NewsDatabase) {

    suspend fun getTopHeadlines(country: String) = apiService.getTopHeadlines(country)
    suspend fun addTopHeadlines(articles: List<Article>) = newsDatabase.newsDao().addTopHeadlines(articles)
    suspend fun getTopHeadlinesFromLocal() = newsDatabase.newsDao().getTopHeadlines()


    suspend fun getSources() = apiService.getSources()
    suspend fun addSources(sources: List<SourceDetail>) = newsDatabase.newsDao().addSources(sources)
    suspend fun getSourcesFromLocal() = newsDatabase.newsDao().getSources()


    suspend fun getEverything(query: String, from: String?, to: String?, sortBy: String?) =
        apiService.getEverything(query, from, to, sortBy)
}