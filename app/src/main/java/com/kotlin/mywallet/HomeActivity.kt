package com.kotlin.mywallet

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.navigation.findNavController
import com.kotlin.mywallet.databinding.ActivityHomeBinding
import com.kotlin.mywallet.login.MainActivity
import com.kotlin.mywallet.personal.Usuario
import kotlinx.android.synthetic.main.drawer_header.*

private const val ADD_GOAL = 3 // PARA AGREGAR META

class HomeActivity : AppCompatActivity() {

    companion object {
        const val TYPE = "TYPE"

        const val PREFS_NAME = "com.kotlin.mywallet"
        const val GOAL = "GOAL"
        const val IS_LOGGED = "IS_LOGGED"
        const val USER_NAME = "USER_NAME"
        const val USER_EMAIL = "USER_EMAIL"

        const val ALERT = "ALERT"
        const val EXIT = "EXIT"
    }

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(binding.root)
    }

}