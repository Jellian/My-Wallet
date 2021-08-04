package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {

    private lateinit var signInBtn: MaterialButton
    private lateinit var emailTxt: EditText
    private lateinit var passwordTxt: EditText
    private lateinit var userNameTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signInBtn = findViewById(R.id.acceptButton)
        userNameTxt = findViewById(R.id.text_user)
        emailTxt = findViewById(R.id.text_email)
        passwordTxt = findViewById(R.id.text_pass)

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
        signInBtn.setOnClickListener( View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            //intent.putExtra("result", amount )
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
    }


}