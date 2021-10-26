package com.kotlin.mywallet.account.list


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.Account
import kotlinx.coroutines.launch

class AccountListViewModel(private val userRepository: UserRepository, username: String): ViewModel() {

    private val editAccountId = MutableLiveData<Int?>()
    val eventEditAccountId = editAccountId

    var accounts: LiveData<List<Account>> = userRepository.getAccountsByUser(username)

    fun getAnyAccountByUser(username: String): Int{
        return userRepository.getAnyAccountByUser(username)
    }

    fun deleteAccount(account: Account){
        viewModelScope.launch {
            userRepository.deleteAccount(account)
            userRepository.deleteChargesByUserAndAccount(account.username, account.accountName)
            userRepository.updateUserGrandTotal(account.username, -account.totalAmount)
        }
    }

    fun onEdit(accountId: Int){
        eventEditAccountId.value = accountId
    }

}