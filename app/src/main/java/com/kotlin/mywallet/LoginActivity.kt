package com.kotlin.mywallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import java.io.Serializable
import java.lang.Exception

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

        val dataUser = intent.getSerializableExtra("dataList") as? ArrayList<*>

        signInBtn.setOnClickListener {
            Log.d("Hola", "ClickListener")
            if (dataUser != null) {
                logIn(dataUser)
            }
        }
    }

    private fun logIn(data: ArrayList<*>){

        if(userNameTxt.text.isNullOrEmpty()){
            Toast.makeText(
                this,
                "Nombre de usuario vacío",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            try{
            if(userNameTxt.text.toString() == data[0] || userNameTxt.text.toString() == data[1] && passwordTxt.text.toString() == data[2]){
                val bundle = Bundle()
                bundle.putString(USER_NAME, data[0].toString())

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
            }}
            catch (e: Exception){
                Toast.makeText(
                    this,
                    "Wrong email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}