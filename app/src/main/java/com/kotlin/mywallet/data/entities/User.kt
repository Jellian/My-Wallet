package com.kotlin.mywallet.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = false) //val id: Int = 0,
    @ColumnInfo val userName: String,
    @ColumnInfo val email: String?,
    @ColumnInfo val password: String?,
)