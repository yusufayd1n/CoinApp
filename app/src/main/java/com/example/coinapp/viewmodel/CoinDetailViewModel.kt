package com.example.coinapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.coinapp.model.Coins
import com.example.coinapp.model.DaoModel
import com.example.coinapp.service.CoinDao
import com.example.coinapp.service.CoinDataBase
import kotlinx.coroutines.launch

class CoinDetailViewModel(application: Application) : BaseViewModel(application) {
    var favoritesCoinUUIDList = listOf<DaoModel>()
    fun addCoinToFavorites(daoModel: DaoModel) {
        launch {
            val dao = CoinDataBase(getApplication()).coinDao()
            dao.addFavorites(daoModel)
            getAllFavoritesCoins()
        }
    }

    fun removeCoinFromFavorites(daoModel: DaoModel) {
        launch {
            val dao = CoinDataBase(getApplication()).coinDao()
            dao.removeFromFavorites(daoModel)
            getAllFavoritesCoins()
        }
    }

    fun getAllFavoritesCoins(){
        launch {
            val dao = CoinDataBase(getApplication()).coinDao()
            favoritesCoinUUIDList = dao.getFavoriteCoins()
        }
    }
}