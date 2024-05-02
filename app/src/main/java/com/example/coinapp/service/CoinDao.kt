package com.example.coinapp.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinapp.model.Coins
import com.example.coinapp.model.DaoModel

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorites(daoModel: DaoModel)

    @Query("SELECT * FROM daomodel")
    suspend fun getFavoriteCoins(): List<DaoModel>

    @Delete
    suspend fun removeFromFavorites(daoModel: DaoModel)
}