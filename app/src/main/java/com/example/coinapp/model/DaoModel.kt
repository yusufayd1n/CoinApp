package com.example.coinapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DaoModel(
    @PrimaryKey
    val uuid: String
)
