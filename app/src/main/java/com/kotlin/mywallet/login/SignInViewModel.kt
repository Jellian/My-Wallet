package com.kotlin.mywallet.login

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.User

class SignInViewModel (private val userRepository: UserRepository, context: Context): ViewModel(){

    private var preferences = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE)

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userRepository.getUserByEmailAndPassword(email, password)
    }

    fun getUserByNameAndPassword(username: String, password: String): User? {
        return userRepository.getUserByNameAndPassword(username, password)
    }

    fun editStringPref( key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

}
