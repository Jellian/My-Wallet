package com.kotlin.mywallet.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.login.MainActivity
import java.text.DecimalFormat
import kotlin.math.roundToInt

class HomeViewModel (private val userRepository: UserRepository, context: Context): ViewModel(){

    private var preferences = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE)
    private val username = preferences.getString(MainActivity.USER_NAME, "").toString()
    private val userEmail = preferences.getString(MainActivity.USER_EMAIL, "").toString()

    var grandTotal = userRepository.getUserGrandTotal(username)
    var actualGoal = userRepository.getActualGoalByUser(username)

    fun getAccountNamesByUser(username: String): List<String> {
        return userRepository.getAccountNamesByUser(username)
    }

    fun editStringPref( key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getUserName(): String{
        return username
    }

    fun getUserEmail(): String{
        return userEmail
    }

}