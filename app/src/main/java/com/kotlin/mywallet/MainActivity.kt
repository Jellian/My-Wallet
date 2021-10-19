package com.kotlin.mywallet

import android.os.Bundle
import android.view.Window
import androidx.fragment.app.FragmentActivity
import androidx.navigation.navOptions


class MainActivity : FragmentActivity() {  // Extends Activity y no AppCompatActivity, para ocultar barra de titulo

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_EMAIL = "USER_EMAIL"

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