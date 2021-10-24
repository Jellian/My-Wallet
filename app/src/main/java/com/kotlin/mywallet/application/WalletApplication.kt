package com.kotlin.mywallet.application

import android.app.Application
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.data.UserRepository

class WalletApplication: Application() {

    val userRepository: UserRepository
    get() = UserRepository( UserDatabase.getInstance(this)!!.userDao)

}



