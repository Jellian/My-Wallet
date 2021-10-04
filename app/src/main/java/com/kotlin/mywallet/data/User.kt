package com.kotlin.mywallet.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val user_name: String?,
    @ColumnInfo val email: String?,
    @ColumnInfo val password: String?,
)