package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val TRANS_TYPE = "com.kotlin.mywallet"

class HomeActivity : AppCompatActivity() {

    private lateinit var welcomeHomeTxt: TextView
    private lateinit var totalAmountTxt: TextView
    private lateinit var addIncomeButton: Button
    private lateinit var addExpenseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bundle = intent.extras
        val name = bundle?.getString(USER_NAME)

        welcomeHomeTxt = findViewById(R.id.welcomeHomeText)
        addIncomeButton = findViewById(R.id.addIncomeButton)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        totalAmountTxt = findViewById(R.id.totalAmountText)

        welcomeHomeTxt.text = "Bienvenido $name"

        addIncomeButton.setOnClickListener(addCharge())
        addExpenseButton.setOnClickListener(addCharge())

    }

    private fun addCharge() = View.OnClickListener { view ->

        val bundle = Bundle()

        when(view.id){
            R.id.addIncomeButton -> bundle.putString(TRANS_TYPE, "ingreso")
            else -> bundle.putString(TRANS_TYPE, "egreso")
        }

        val intent = Intent(this, AddChargeActivity::class.java)
        intent.putExtras(bundle)
        //startActivity(intent)

        startActivityForResult(intent, 1 )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            val a: Float = data?.getStringExtra("result")?.toFloat()?: 0.0f
            val b: Float = totalAmountTxt.text.toString().toFloat()

            totalAmountTxt.text = (a+b).toString()

        }
    }

}