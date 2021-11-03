package com.kotlin.mywallet.charge.list

import androidx.lifecycle.ViewModel
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.Account

class DetailViewModel (private val userRepository: UserRepository, username: String, accountName: String): ViewModel(){

    var accountTotalAmount = userRepository.getAccountTotalAmount(accountName, username)

    fun getAccountByNameAndUser(accountName: String, username: String): Account {
        return userRepository.getAccountByNameAndUser(accountName, username)
    }

}