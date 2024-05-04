package com.example.coinapp.service

import com.example.coinapp.model.Coins
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinAPI {
    @GET("v2/coins")
    fun getCoins(
        @Query("offset") offset: Int = 0
    ): Single<Coins>

    @GET("v2/coins")
    fun getCoinsByOrder(
        @Query("offset") offset: Int = 0,
        @Query("orderBy") orderBy: String
    ): Single<Coins>
}