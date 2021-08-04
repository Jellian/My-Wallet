package com.kotlin.mywallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    private lateinit var signInBtn: MaterialButton
    private lateinit var passwordTxt: EditText
    private lateinit var userNameTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInBtn = findViewById(R.id.acceptButton)
        userNameTxt = findViewById(R.id.text_user)
        passwordTxt = findViewById(R.id.text_pass)

        signInBtn.setOnClickListener {
            logIn()
        }
    }

    private fun logIn(){

        if(userNameTxt.text.isNullOrEmpty()){
            Toast.makeText(
                this,
                "Nombre de usuario vacío",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            if (userNameTxt.text.toString() == "prueba@email.com" && passwordTxt.text.toString() == "password") {

                val bundle = Bundle()
                bundle.putString(USER_NAME, userNameTxt.text.toString())

                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtras(bundle)
                }
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
}