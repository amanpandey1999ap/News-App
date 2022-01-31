package com.aman.newsapp.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("app:image")
fun image(view: ImageView, url: String?) {
    if (url?.isNotEmpty() == true)
        view.let { Glide.with(view.context).load(url).into(it) }

}
