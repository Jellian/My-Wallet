package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {

    private lateinit var acceptBtn: MaterialButton
    private lateinit var emailTxt: EditText
    private lateinit var passwordTxt: EditText
    private lateinit var userNameTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        acceptBtn = findViewById(R.id.acceptButton)
        userNameTxt = findViewById(R.id.text_user)
        emailTxt = findViewById(R.id.text_email)
        passwordTxt = findViewById(R.id.text_pass)

        acceptBtn.setOnClickListener( View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)

            val dataList = ArrayList<String>()
            dataList.add(userNameTxt.text.toString())
            dataList.add(emailTxt.text.toString())
            dataList.add(passwordTxt.text.toString())

            if(dataList.none { it.isEmpty() }){
                intent.putExtra("dataList", dataList)
                setResult(Activity.RESULT_OK, intent)
            }
            else setResult(Activity.RESULT_CANCELED, intent)

            finish()
        })
    }


}