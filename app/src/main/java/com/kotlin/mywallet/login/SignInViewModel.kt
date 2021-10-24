package com.kotlin.mywallet.login

import androidx.lifecycle.ViewModel
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.User

class SignInViewModel (private val userRepository: UserRepository): ViewModel(){

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userRepository.getUserByEmailAndPassword(email, password)
    }

    fun getUserByNameAndPassword(username: String, password: String): User? {
        return userRepository.getUserByNameAndPassword(username, password)
    }

}
