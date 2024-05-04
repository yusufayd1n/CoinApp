package com.example.coinapp.service

import com.example.coinapp.BuildConfig
import com.example.coinapp.interceptor.AuthInterceptor
import com.example.coinapp.model.Coins
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CoinAPIService {
    private val BASE_URL = "https://api.coinranking.com/"

    val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(BuildConfig.API_KEY))
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CoinAPI::class.java)

    fun getData(offset: Int): Single<Coins> {
        return api.getCoins(offset)
    }

    fun getDataByOrder(offset: Int, orderBy: String): Single<Coins> {
        return api.getCoinsByOrder(offset = offset, orderBy = orderBy)
    }
}