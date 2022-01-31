package com.aman.newsapp.di

import android.content.Context
import androidx.databinding.ktx.BuildConfig
import com.aman.newsapp.db.NewsDatabase
import com.aman.newsapp.networking.ApiService
import com.aman.newsapp.networking.AuthBasicInterceptor
import com.aman.newsapp.utils.Constants
import com.aman.newsapp.utils.NewsApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    fun provideOkHttpClient(authBasicInterceptor: AuthBasicInterceptor) = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authBasicInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .addInterceptor(authBasicInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = NewsDatabase.getDatabase(appContext)
}