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
            userRepository.updateUserGrandTotal(account.username, account.totalAmount)
        }
    }

    fun getAccountById(accountId: Int): Account{
        return userRepository.getAccountById(accountId)
    }

    fun updateAccountById(accountToEdit: Account, editedAccount: Account){
        viewModelScope.launch {

            val diff = (editedAccount.initialAmount - accountToEdit.initialAmount)
            editedAccount.totalAmount = accountToEdit.totalAmount + diff

            userRepository.updateAccountById(editedAccount)
            userRepository.updateUserGrandTotal(accountToEdit.username, diff)
            userRepository.updateChargesAccountName(editedAccount.accountName, accountToEdit.accountName, accountToEdit.username)
        }
    }

}