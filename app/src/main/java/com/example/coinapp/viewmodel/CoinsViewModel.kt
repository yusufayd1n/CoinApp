package com.example.coinapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.coinapp.model.Coins
import com.example.coinapp.model.DaoModel
import com.example.coinapp.service.CoinAPIService
import com.example.coinapp.service.CoinDataBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CoinsViewModel(application: Application) : BaseViewModel(application) {
    private val coinAPIService = CoinAPIService()
    private val disposable = CompositeDisposable()

    val coins = MutableLiveData<List<Coins.Data.Coin>>()
    val coinError = MutableLiveData<Boolean>()
    val coinsLoading = MutableLiveData<Boolean>()
    var favoritesCoinUUIDList = listOf<DaoModel>()
    var offset = 0
    fun getCoinsFromAPI() {
        coinsLoading.value = true

        disposable.add(
            coinAPIService.getData(offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Coins>() {
                    override fun onSuccess(t: Coins) {
                        val currentList = coins.value ?: emptyList()
                        coins.value = currentList + t.data.coins
                        coinError.value = false
                        coinsLoading.value = false
                        offset += 50
                    }

                    override fun onError(e: Throwable) {
                        coinsLoading.value = false
                        coinError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    fun getAllFavoritesCoins() {
        launch {
            val dao = CoinDataBase(getApplication()).coinDao()
            favoritesCoinUUIDList = dao.getFavoriteCoins()
        }

    }
}