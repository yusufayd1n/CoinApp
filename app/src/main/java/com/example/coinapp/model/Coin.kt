package com.example.coinapp.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Coin(
    val id : String = "123"
) : Parcelable
