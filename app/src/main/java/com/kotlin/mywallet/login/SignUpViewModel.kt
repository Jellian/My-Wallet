package com.kotlin.mywallet.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.User
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository): ViewModel() {

    fun insertUser(user: User){
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

}