package com.kotlin.mywallet

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.login.MainActivity
import kotlinx.coroutines.launch

class GoalViewModel (private val userRepository: UserRepository, context: Context): ViewModel(){

    private var preferences = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE)

    private val username = preferences.getString(MainActivity.USER_NAME, "").toString()

    var actualGoal = userRepository.getActualGoalByUser(username)

    fun updateActualGoalByUser(newGoal: Float){
        viewModelScope.launch {
            userRepository.updateActualGoalByUser(username, newGoal)
        }
    }
}