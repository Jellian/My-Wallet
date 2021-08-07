package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.kotlin.mywallet.finance.Egreso
import com.kotlin.mywallet.finance.Ingreso

class AddChargeActivity : AppCompatActivity() {

    private lateinit var headerActionTextView: TextView
    private lateinit var amountEditText: EditText
    private lateinit var noteEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var addChargeButton: Button
    private lateinit var accountSpinner: Spinner

    private var chargeType: Int? = 0
    private var category: String = ""
    private var account: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_charge)

        amountEditText = findViewById(R.id.editText_addCharge_amount)
        noteEditText = findViewById(R.id.editText_addCharge_note)
        headerActionTextView = findViewById(R.id.textView_addCharge_headerAction)
        categorySpinner = findViewById(R.id.spinner_addCharge_categories)
        accountSpinner = findViewById(R.id.spinner_addCharge_accounts)
        addChargeButton= findViewById(R.id.button_addCharge_add)

        chargeType = intent.getIntExtra(HomeActivity.TYPE, 0)
        val accounts = intent.getSerializableExtra(HomeActivity.ACCOUNT_LIST) as? ArrayList<*>

        val categories : List<String>

        if (chargeType != +1) {
            categories = Categories.expendOptions
            headerActionTextView.text = getString(R.string.anadir_egreso)
        }
        else {
            categories = Categories.incomeOptions
            headerActionTextView.text = getString(R.string.anadir_ingreso)
        }

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
                account = accounts?.get(position) as String
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
        val note = noteEditText.text.toString()

        intent.putExtra(HomeActivity.ACCOUNT, account)

        if (chargeType == 1) {
            val charge = Ingreso(amount, category, note)
            intent.putExtra(HomeActivity.TYPE, 1 )
            intent.putExtra(HomeActivity.CHARGE, charge )
        }
        else{
            val charge = Egreso(-amount, category, note)
            intent.putExtra(HomeActivity.TYPE, -1 )
            intent.putExtra(HomeActivity.CHARGE, charge )
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}