package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class AddAccountActivity : AppCompatActivity() {

    private lateinit var accountNameEditText: EditText
    private lateinit var initialAmountEditText: EditText
    private lateinit var acceptButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)

        acceptButton= findViewById(R.id.button_addAccount_accept)
        accountNameEditText = findViewById(R.id.textView_addAccount_accountName)
        initialAmountEditText = findViewById(R.id.textView_addAccount_initialAmount)

        acceptButton.setOnClickListener(returnNewAccountData())

    }

    private fun returnNewAccountData() = View.OnClickListener {

        if(accountNameEditText.text.isNullOrEmpty() || initialAmountEditText.text.isNullOrEmpty()){
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
        }
        else {
            val intent = Intent(this, AddAccountActivity::class.java)
            val amount = initialAmountEditText.text.toString()
            val name = accountNameEditText.text.toString()

            intent.putExtra(HomeActivity.NEW_ACCOUNT_NAME, name)
            intent.putExtra(HomeActivity.NEW_ACCOUNT_AMOUNT, amount)
            setResult(Activity.RESULT_OK, intent)
            Toast.makeText(this, "Cuenta $name agregada.", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}