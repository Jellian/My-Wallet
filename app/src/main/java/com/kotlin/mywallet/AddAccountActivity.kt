package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import java.time.temporal.TemporalAmount

class AddAccountActivity : AppCompatActivity() {

    private lateinit var textName: EditText
    private lateinit var textInitialAmount: EditText
    private lateinit var acceptButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)

        acceptButton= findViewById(R.id.acceptButton)
        textName = findViewById(R.id.text_name)
        textInitialAmount = findViewById(R.id.text_initial_amount)

        acceptButton.setOnClickListener(returnNewAccountData())

    }

    private fun returnNewAccountData() = View.OnClickListener {

        if(textName.text.isNullOrEmpty() || textInitialAmount.text.isNullOrEmpty()){
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
        }
        else {
            val intent = Intent(this, AddAccountActivity::class.java)
            val amount = textInitialAmount.text.toString()
            val name = textName.text.toString()

            intent.putExtra("name", name)
            intent.putExtra("amount", amount)
            setResult(Activity.RESULT_OK, intent)
            Toast.makeText(this, "Cuenta $name agregada.", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}