package com.kotlin.mywallet.add.entity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.Charge
import kotlinx.coroutines.launch

class AddChargeViewModel(private val userRepository: UserRepository): ViewModel() {

    fun getAccountNamesByUser(username: String): List<String> {
        return userRepository.getAccountNamesByUser(username)
    }

    fun insertCharge(charge: Charge){
        viewModelScope.launch {
            userRepository.insertCharge(charge)
            userRepository.updateAccountTotalAmount(charge.accountName, charge.username, charge.amount)
            userRepository.updateUserGrandTotal(charge.username, charge.amount)
        }
    }

}