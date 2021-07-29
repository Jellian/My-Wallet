package com.kotlin.mywallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var signinBtn: Button
    private lateinit var emailTxt: EditText
    private lateinit var passwordTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signinBtn = findViewById(R.id.loginButton)


        signinBtn.setOnClickListener(onSignin())
    }


    private fun onSignin() = View.OnClickListener { view ->
        emailTxt = findViewById(R.id.editTextEmailAddress)
        passwordTxt = findViewById(R.id.editTextPassword)
        if (emailTxt.text.toString().equals("prueba@email.com") && passwordTxt.text.toString().equals("password")) {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            intent.putExtra("email", emailTxt.text)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(
                this,
                "Wrong email or password",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}