package com.kotlin.mywallet.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlin.mywallet.finance.Cargo

@Entity
data class Account (

    @PrimaryKey(autoGenerate = false) //val id: Int = 0,
    @ColumnInfo val accountName: String,
    @ColumnInfo val initialAmount: Float,
    @ColumnInfo val userName: String

    ) {

    @ColumnInfo var currency = "MXN"
    @ColumnInfo
    var totalAmount = 0f

    init {
        totalAmount = initialAmount
    }

}