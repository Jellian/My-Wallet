package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {

    private lateinit var acceptButton: MaterialButton
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var userNameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        acceptButton = findViewById(R.id.button_register_accept)
        userNameEditText = findViewById(R.id.editText_register_userName)
        emailEditText = findViewById(R.id.editText_register_email)
        passwordEditText = findViewById(R.id.editText_register_password)

        acceptButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)

            intent.putExtra(MainActivity.USER_NAME, userNameEditText.text.toString())
            intent.putExtra(MainActivity.USER_EMAIL, emailEditText.text.toString())
            intent.putExtra(MainActivity.USER_PASSWORD, passwordEditText.text.toString())

            setResult(Activity.RESULT_OK, intent)

            finish()
        }
    }


}