package com.kotlin.mywallet.home

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.User
import com.kotlin.mywallet.login.MainActivity
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.roundToInt

class HomeViewModel (private val userRepository: UserRepository, context: Context): ViewModel(){

    private var preferences = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE)
    private val username = preferences.getString(MainActivity.USER_NAME, "").toString()
    private val userEmail = preferences.getString(MainActivity.USER_EMAIL, "").toString()
    private var pictureUriReference: Uri? = null

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

    fun getUriRefByUser(username: String): Uri?{
        return userRepository.getUriRefByUser(username)?.toUri()
    }

    fun updateUriRefByUser(username: String, uriRef: Uri?){
        viewModelScope.launch {
            userRepository.updateUriRefByUser(username, uriRef.toString())
        }
    }

}