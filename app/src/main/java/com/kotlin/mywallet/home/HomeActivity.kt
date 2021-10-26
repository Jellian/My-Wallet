package com.kotlin.mywallet.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.mywallet.databinding.ActivityHomeBinding
import com.kotlin.mywallet.login.MainActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(binding.root)
    }

}