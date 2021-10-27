package com.kotlin.mywallet.login

import android.os.Bundle
import android.view.Window
import androidx.fragment.app.FragmentActivity
import androidx.navigation.navOptions
import com.kotlin.mywallet.R


class MainActivity : FragmentActivity() { // Fragment Activity

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_EMAIL = "USER_EMAIL"
        const val CHANNEL_ANNOUNCES = "CHANNEL_ANNOUNCES"

        const val ACCOUNT = "ACCOUNT"
        const val USERNAME = "USERNAME"

        const val TYPE = "TYPE"

        const val PREFS_NAME = "com.kotlin.mywallet"
        const val GOAL = "GOAL"
        const val IS_LOGGED = "IS_LOGGED"

        const val ID = "ID"
        const val ENTITY = "ENTITY"

        const val EDIT = "EDIT"

        const val ALERT = "ALERT"
        const val EXIT = "EXIT"
        const val DELETE = "DELETE"


        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
    }

}