package com.aman.newsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent


fun Context.isInternetAvailable(): Boolean {
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
        return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        ) ?: false
    }
}

fun Context.openCustomTab(url: String){
    val builder =  CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
}
