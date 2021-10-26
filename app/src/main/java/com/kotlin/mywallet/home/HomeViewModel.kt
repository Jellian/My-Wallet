package com.kotlin.mywallet.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.login.MainActivity

class HomeViewModel (private val userRepository: UserRepository, context: Context): ViewModel(){

    private var preferences = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE)

    fun getAccountNamesByUser(username: String): List<String> {
        return userRepository.getAccountNamesByUser(username)
    }

    fun getFloatPref( key: String, default: Float = 0.0f): Float {
        return preferences.getFloat(key, default)
    }

    fun editStringPref( key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

}