package com.kotlin.mywallet.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account @JvmOverloads constructor(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo var accountName: String,
    @ColumnInfo var initialAmount: Float,
    @ColumnInfo var username: String

    ) {

    @ColumnInfo var currency = "MXN"
    @ColumnInfo var totalAmount = 0f

    init {
        totalAmount = initialAmount
    }

}