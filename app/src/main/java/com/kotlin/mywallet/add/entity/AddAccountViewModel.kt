package com.kotlin.mywallet.add.entity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.Account
import kotlinx.coroutines.launch

class AddAccountViewModel(private val userRepository: UserRepository): ViewModel() {

    fun getAccountNamesByUser(username: String): List<String> {
        return userRepository.getAccountNamesByUser(username)
    }

    fun insertAccount(account: Account){
        viewModelScope.launch {
            userRepository.insertAccount(account)
        }
    }

}