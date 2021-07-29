package com.kotlin.mywallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class HomeActivity : AppCompatActivity() {

    private lateinit var welcomeHomeTxt: TextView
    private lateinit var totalAmountTxt: TextView
    private lateinit var addIncomeButton: Button
    private lateinit var addExpenseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        addIncomeButton = findViewById(R.id.addIncomeButton)
        addExpenseButton = findViewById(R.id.addExpenseButton)

        addIncomeButton.setOnClickListener(addChargeIncome())
        addExpenseButton.setOnClickListener(addChargeExpensive())
    }

    private fun addChargeIncome() = View.OnClickListener { view ->
        val intent = Intent(this@HomeActivity, AddChargeActivity::class.java)
        intent.putExtra("chargeType", "income")
        startActivity(intent)
    }

    private fun addChargeExpensive() = View.OnClickListener { view ->
        val intent = Intent(this@HomeActivity, AddChargeActivity::class.java)
        intent.putExtra("chargeType", "expense")
        startActivity(intent)
    }
}