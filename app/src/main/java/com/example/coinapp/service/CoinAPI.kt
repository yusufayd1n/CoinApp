package com.example.coinapp.service

import com.example.coinapp.model.Coins
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinAPI {
    @GET("v2/coins")
    fun getCoins(
        @Query("offset") offset: Int = 0,
        @Query("access_token") accessToken: String = "coinranking1253b1a33703587e28428a74642b513d31c1071fc649d466"
    ): Single<Coins>
}