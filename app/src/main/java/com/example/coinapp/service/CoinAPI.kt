package com.example.coinapp.service

import com.example.coinapp.model.Coins
import io.reactivex.Single
import retrofit2.http.GET

interface CoinAPI {

    @GET("v2/coins")
    fun getCoins() : Single<Coins>
}