package com.kotlin.mywallet

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val USER_NAME = "com.kotlin.mywallet"

class MainActivity : AppCompatActivity() {

    private lateinit var signInBtn: Button
    private lateinit var emailTxt: EditText
    private lateinit var passwordTxt: EditText
    private lateinit var userNameTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signInBtn = findViewById(R.id.loginButton)
        userNameTxt = findViewById(R.id.editUserName)
        emailTxt = findViewById(R.id.editTextEmailAddress)
        passwordTxt = findViewById(R.id.editTextPassword)

        // Aqui se hacia logIn con el teclado de android.
//        passwordTxt.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
//                if (event.action == KeyEvent.ACTION_DOWN) {     // Cada que se presiona una tecla
//                    return when (keyCode) {
//                        KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {   // Enter o flecha :v
//                            logIn()
//                            true
//                        }
//                        else -> {
//                            false
//                        }
//                    }
//                }
//                return false
//            }
//        })

        signInBtn.setOnClickListener( View.OnClickListener { logIn() })
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
            if (emailTxt.text.toString() == "prueba@email.com" && passwordTxt.text.toString() == "password") {

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