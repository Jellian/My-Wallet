package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.io.Serializable

const val USER_NAME = "com.kotlin.mywallet"

class MainActivity : Activity() {  // Extends Activity y no AppCompatActivity, para ocultar barra de titulo

    private lateinit var regButton: MaterialButton
    private lateinit var ingButton: MaterialButton

    private var dataUser= ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        regButton= findViewById(R.id.reg_button)
        ingButton= findViewById(R.id.ing_button)

        regButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, 1)
        }

        ingButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtra("dataList", dataUser)
            }
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && data!= null)
            dataUser = data.getSerializableExtra("dataList") as ArrayList<String>
    }
}