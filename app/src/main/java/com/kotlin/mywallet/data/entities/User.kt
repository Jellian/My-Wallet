package com.kotlin.mywallet.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value= ["email"], unique = true)])
data class User @JvmOverloads constructor(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val username: String,
    @ColumnInfo val email: String?,
    @ColumnInfo val password: String?
)