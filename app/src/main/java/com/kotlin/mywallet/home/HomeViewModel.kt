package com.kotlin.mywallet.home

import androidx.lifecycle.ViewModel
import com.kotlin.mywallet.data.UserRepository

class HomeViewModel (private val userRepository: UserRepository): ViewModel(){

    fun getAccountNamesByUser(username: String): List<String> {
        return userRepository.getAccountNamesByUser(username)
    }

}