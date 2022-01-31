package com.aman.newsapp.networking

 import com.aman.newsapp.BuildConfig
 import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthBasicInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()
        requestBuilder.addHeader("x-api-key", BuildConfig.API_KEY)

        return chain.proceed(requestBuilder.build())
    }
}