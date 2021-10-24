package com.kotlin.mywallet.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Charge @JvmOverloads constructor(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo var amount: Float = 0.0f,
    @ColumnInfo var category: String,
    @ColumnInfo var note: String = "",
    @ColumnInfo var date: String,
    @ColumnInfo var accountName: String,
    @ColumnInfo var username: String
    ){

    @ColumnInfo
    var type: String = ""

    init{
        type = if(amount<0) "Egreso"
        else "Ingreso"
    }

}
