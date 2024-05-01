package com.example.coinapp.util

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.coinapp.R

fun ImageView.loadUrl(url: String) {
    val imageLoader = ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()

    val request = ImageRequest.Builder(context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.ic_back)
        .error(R.drawable.ic_notification)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}