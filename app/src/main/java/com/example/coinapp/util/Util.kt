package com.example.coinapp.util

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.coinapp.R
import java.text.DecimalFormat

fun ImageView.loadUrl(url: String) {
    val imageLoader = ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()

    val request = ImageRequest.Builder(context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.ic_loading)
        .error(R.drawable.ic_error)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

fun formatCurrency(value: String): String {
    val formattedValue = value.toDouble()
    val formatter = DecimalFormat("#,##0.00")
    return formatter.format(formattedValue)
}