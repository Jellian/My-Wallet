package com.kotlin.mywallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private lateinit var signInButton: MaterialButton
    private lateinit var passwordEditText: EditText
    private lateinit var userNameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton = findViewById(R.id.button_register_accept)
        userNameEditText = findViewById(R.id.editText_register_userName)
        passwordEditText = findViewById(R.id.editText_register_password)

        val userName = intent.getStringExtra(MainActivity.USER_NAME)
        val userEmail = intent.getStringExtra(MainActivity.USER_EMAIL)
        val userPassword = intent.getStringExtra(MainActivity.USER_PASSWORD)

        signInButton.setOnClickListener {
                logIn(userName, userEmail, userPassword)
        }
    }

    private fun logIn(userName: String?, userEmail: String?, userPassword: String?){

        if(userNameEditText.text.isNullOrEmpty()){
            Toast.makeText(this, "Nombre de usuario vacío", Toast.LENGTH_SHORT).show()
        }
        else{
            if (userNameEditText.text.toString() == userName || userNameEditText.text.toString() == userEmail
                && passwordEditText.text.toString() == userPassword ) {

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(MainActivity.USER_NAME, userName)
                intent.putExtra(MainActivity.USER_EMAIL, userEmail)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}