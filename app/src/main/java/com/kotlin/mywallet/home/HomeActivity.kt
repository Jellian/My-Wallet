package com.kotlin.mywallet.home

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

        const val ENTITY = "ENTITY"

        const val ALERT = "ALERT"
        const val EXIT = "EXIT"
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(binding.root)
    }

    fun showDialog(title:String, message:String, type: String = ALERT){
        if (type == ALERT) AlertDialog.Builder( this ).setTitle(title).setMessage(message).setPositiveButton("OK") { _, _ -> }.create().show()
        else if (type == EXIT)
            AlertDialog.Builder( this).setTitle(title).setMessage(message).setPositiveButton("Sí") { _, _ ->
                preferences.edit().putString(IS_LOGGED, "FALSE").apply()
                finish()
            }.setNegativeButton("No", null).create().show()
    }

}