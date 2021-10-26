package com.kotlin.mywallet.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value= ["email"], unique = true), Index(value = ["username"], unique = true)])
data class User @JvmOverloads constructor(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo var username: String,
    @ColumnInfo var email: String?,
    @ColumnInfo var password: String?
) {
    @ColumnInfo var grandTotal: Float = 0.0f
}