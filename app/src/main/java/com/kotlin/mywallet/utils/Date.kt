package com.kotlin.mywallet.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Date {

    private var value = ""

    @RequiresApi(Build.VERSION_CODES.O)
    fun now() : String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        this.value = current.format(formatter)

        return this.value
    }

}