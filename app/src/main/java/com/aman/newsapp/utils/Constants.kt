package com.aman.newsapp.utils

class Constants {
    companion object {

        const val BASE_URL = "https://newsapi.org/v2/"

        //Sort Everything Section
        const val RELEVANCY = "relevancy"
        const val POPULARITY = "popularity"
        const val NEWEST = "publishedAt"

        //Top Headlines Default Query
        const val DEFAULT_COUNTRY = "us"

        const val relevancy = 0
        const val newest = 1
        const val popularity = 2
        const val from = 0
        const val to = 1

        const val SPAN_COUNT = 2

        // Tab Headers
        const val TOP_HEADLINES = "Top Headlines"
        const val EVERYTHING = "Everything"
        const val SOURCES = "Sources"
    }
}