package com.kotlin.mywallet.charge.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.Charge
import kotlinx.coroutines.launch

class ChargeListViewModel(private val userRepository: UserRepository): ViewModel() {

    private var charges: List<Charge> = listOf()

    fun getChargeListByUserAndAccount(username: String, accountName: String): List<Charge>{
        charges = userRepository.getChargesByUserAndAccount(username, accountName)
        return charges
    }

}