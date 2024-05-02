package com.example.coinapp.service

import com.example.coinapp.model.Coins
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CoinAPIService {
    private val BASE_URL = "https://api.coinranking.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CoinAPI::class.java)

    fun getData(offset: Int) : Single<Coins> {
        return api.getCoins(offset)
    }
}