package com.kotlin.mywallet

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.kotlin.mywallet.finance.Egreso
import com.kotlin.mywallet.finance.Ingreso

class AddChargeActivity : AppCompatActivity() {

    private lateinit var headerActionText: TextView
    private lateinit var amountEditText: EditText
    private lateinit var editTextNote: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var addChargeButton: Button
    private lateinit var accountSpinner: Spinner

    private var chargeType: String? = "ingreso"
    private var category: String = ""
    private var cuenta: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_charge)

        amountEditText = findViewById(R.id.amountEditText)
        editTextNote = findViewById(R.id.noteEditText)
        headerActionText = findViewById(R.id.headerActionText)
        categorySpinner = findViewById(R.id.categorySpinner)
        accountSpinner = findViewById(R.id.accountsSpinner)
        addChargeButton= findViewById(R.id.addChargeButton)

        //val bundle = intent.extras
        chargeType = intent.getStringExtra("type")//bundle?.getString(TRANS_TYPE)
        val accounts = intent.getSerializableExtra("accountsList") as? ArrayList<*>

        headerActionText.text = "Añadir $chargeType"

        val categories = if (chargeType != "ingreso") Categories.expendOptions
        else Categories.incomeOptions

        addChargeButton.setOnClickListener( createCharge())

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = categories[position]
            }
        }

        accountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cuenta = accounts?.get(position) as String
            }
        }

        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        if(accounts != null) {
            val accountAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, accounts.toList())
            accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            accountSpinner.adapter = accountAdapter
        }
    }

    private fun createCharge() = View.OnClickListener {

        val intent = Intent(this, AddChargeActivity::class.java)

        val amount = amountEditText.text.toString().toFloat()
        val note = editTextNote.text.toString()

        intent.putExtra("cuenta", cuenta)

        if (chargeType == "ingreso") {
            val cargo = Ingreso(amount, category, note)
            intent.putExtra("type", 1 )
            intent.putExtra("cargo", cargo )
            //Toast.makeText(this, "Ingreso creado. ${amount}MXN a cuenta $cuenta en categoría $category.", Toast.LENGTH_LONG).show()
        }
        else{
            val cargo = Egreso(-amount, category, note)
            intent.putExtra("type", -1 )
            intent.putExtra("cargo", cargo )
            //Toast.makeText(this, "Egreso creado. -${amount}MXN a $cuenta en categoría $category", Toast.LENGTH_LONG).show()
            //showDialog("Egreso creado.", "-${amount}MXN a cuenta $cuenta en categoría $category.")
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}