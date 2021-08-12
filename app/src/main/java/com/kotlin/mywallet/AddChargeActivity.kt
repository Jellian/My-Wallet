package com.kotlin.mywallet

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kotlin.mywallet.finance.Cargo
import java.util.*
import kotlin.collections.ArrayList


class AddChargeActivity : AppCompatActivity() {

    private lateinit var headerActionTextView: TextView
    private lateinit var amountEditText: EditText
    private lateinit var noteEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var addChargeButton: Button
    private lateinit var accountSpinner: Spinner
    private lateinit var dateTextView: TextView

    private var chargeType: Int? = 0
    private var category: String = ""
    private var accountName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_charge)

        amountEditText = findViewById(R.id.editText_addCharge_amount)
        noteEditText = findViewById(R.id.editText_addCharge_note)
        headerActionTextView = findViewById(R.id.textView_addCharge_headerAction)
        categorySpinner = findViewById(R.id.spinner_addCharge_categories)
        accountSpinner = findViewById(R.id.spinner_addCharge_accounts)
        addChargeButton = findViewById(R.id.button_addCharge_add)
        dateTextView = findViewById(R.id.textView_addCharge_date)

        val appBar = findViewById<Toolbar>(R.id.toolbar_addCharge_appBar)
        setSupportActionBar(appBar)

        val calendar: Calendar = Calendar.getInstance()
        val actualYear: Int = calendar.get(Calendar.YEAR)
        val actualMonth: Int = calendar.get(Calendar.MONTH)
        val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)

        "$dayOfMonth/ ${actualMonth+1}/ $actualYear".also { dateTextView.text = it }

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

        dateTextView.setOnClickListener {
            val dateListener = { datePicker: DatePicker, year: Int, month: Int, day: Int -> dateTextView.text = ("$day/ ${month+1}/ $year")}

            val datePickerDialog = DatePickerDialog(this, dateListener, actualYear, actualMonth, dayOfMonth)
            datePickerDialog.show()
        }

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
                accountName = accounts?.get(position) as String
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

        appBar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }

    private fun createCharge() = View.OnClickListener {

        val intent = Intent(this, AddChargeActivity::class.java)

        val amount = amountEditText.text.toString().toFloat()
        val note = noteEditText.text.toString()

        val charge = if (chargeType == 1) { Cargo(amount, category, note, dateTextView.text.toString()) }
        else{ Cargo(-amount, category, note, dateTextView.text.toString()) }

        intent.putExtra(HomeActivity.ACCOUNT, accountName)
        intent.putExtra(HomeActivity.TYPE, chargeType )
        intent.putExtra(HomeActivity.CHARGE, charge )

        setResult(Activity.RESULT_OK, intent)
        finish()

    }
}
