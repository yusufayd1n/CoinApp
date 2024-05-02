package com.example.coinapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coins(
    val `data`: Data,
    val status: String
) : Parcelable {
    @Parcelize
    data class Data(
        val coins: List<Coin>,
        val stats: Stats
    ) : Parcelable {
        @Parcelize
        data class Coin(
            val `24hVolume`: String,
            val btcPrice: String,
            val change: String,
            val coinrankingUrl: String,
            val color: String,
            val contractAddresses: List<String>,
            val iconUrl: String,
            val listedAt: Int,
            val lowVolume: Boolean,
            val marketCap: String,
            val name: String,
            val price: String,
            val rank: Int,
            val sparkline: List<String>,
            val symbol: String,
            val tier: Int,
            val uuid: String,
            var isFavorite: Boolean = false
        ) : Parcelable

        @Parcelize
        data class Stats(
            val total: Int,
            val total24hVolume: String,
            val totalCoins: Int,
            val totalExchanges: Int,
            val totalMarketCap: String,
            val totalMarkets: Int
        ) : Parcelable
    }
}


