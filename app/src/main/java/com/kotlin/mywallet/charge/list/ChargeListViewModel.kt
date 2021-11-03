package com.kotlin.mywallet.charge.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.Charge
import kotlinx.coroutines.launch

class ChargeListViewModel(private val userRepository: UserRepository, username: String, accountName: String): ViewModel() {

    private val editChargeId = MutableLiveData<Int?>()
    val eventEditChargeId = editChargeId

    var charges: LiveData<List<Charge>> = userRepository.getChargesByUserAndAccount(username, accountName)


    fun onEdit(chargeId: Int){
        eventEditChargeId.value = chargeId
    }

    fun deleteCharge(charge: Charge){
        viewModelScope.launch {
            userRepository.deleteCharge(charge)
            userRepository.updateAccountTotalAmount(charge.accountName, charge.username, -charge.amount)
            userRepository.updateUserGrandTotal(charge.username, -charge.amount)
        }
    }
}