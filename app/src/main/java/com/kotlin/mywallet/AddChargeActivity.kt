package com.kotlin.mywallet

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class AddChargeActivity : AppCompatActivity() {

    private lateinit var headerActionText: TextView
    private lateinit var amountEditText: EditText
    private lateinit var editTextNote: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var addChargeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_charge)

        amountEditText = findViewById(R.id.amountEditText)
        editTextNote = findViewById(R.id.editTextNote)
        headerActionText = findViewById(R.id.headerActionText)
        categorySpinner = findViewById(R.id.categorySpinner)
        addChargeButton= findViewById(R.id.addChargeButton)

        val bundle = intent.extras
        val chargeType = bundle?.getString(TRANS_TYPE)

        headerActionText.text = "Añadir $chargeType"

        val categories = if (chargeType != "ingreso"){
            Categories.expendOptions
        }
        else {
            Categories.incomeOptions
        }

        addChargeButton.setOnClickListener {
            val intent = Intent(this, AddChargeActivity::class.java)
            var amount = amountEditText.text.toString()

            if (chargeType != "ingreso")
                amount = "-$amount"

            intent.putExtra("result", amount )

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                showDialog("No seleccionaste categoria","Vuelve a desplegar la lista y asegúrate de elegir correctamente alguna")
                Toast.makeText(applicationContext, "No hay categoria" , Toast.LENGTH_LONG).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(applicationContext , categories[position] , Toast.LENGTH_LONG).show()
            }
        }

        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter
    }

    private fun showDialog(title:String,message:String){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK"){dialogInterface, which -> }
            .create()
            .show()
    }

}