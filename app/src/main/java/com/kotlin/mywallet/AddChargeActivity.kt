package com.kotlin.mywallet

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.data.entities.Charge
import com.kotlin.mywallet.finance.Cargo
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
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
    private var username: String = ""
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

        "${actualMonth+1}/ $dayOfMonth/ $actualYear".also { dateTextView.text = it }

        //dateTextView.text = String.format("%02d/%02d/%04d", actualMonth+1, dayOfMonth, actualYear)

        chargeType = intent.getIntExtra(HomeActivity.TYPE, 0)
        username = intent.getStringExtra(HomeActivity.USER_NAME).toString()

        val categories : List<String>

        getAccounts()

        if (chargeType != +1) {
            categories = Categories.expendOptions
            headerActionTextView.text = getString(R.string.anadir_egreso)
        }
        else {
            categories = Categories.incomeOptions
            headerActionTextView.text = getString(R.string.anadir_ingreso)
        }

        addChargeButton.setOnClickListener( createCharge() )

        dateTextView.setOnClickListener {
//            val dateListener = { datePicker: DatePicker, year: Int, month: Int, day: Int ->
//                //dateTextView.text = (String.format("%02d/%02d/%04d",month+1,day, year))
//
//            }
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

        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter


        appBar.setNavigationOnClickListener { finish() }
    }

    private fun getAccounts() {

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(
            Runnable {
                val accountList = UserDatabase.getInstance(this)
                    ?.userDao
                    ?.findAccountNamesByUser(username)

                Handler(Looper.getMainLooper()).post(Runnable {
                    if(accountList != null) {
                        val accountAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, accountList.toList())
                        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        accountSpinner.adapter = accountAdapter
                    }

                    accountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            accountName = accountList?.get(position) as String
                        }
                    }
                })
            })
    }

    private fun createCharge() = View.OnClickListener {
        //validar que el monto es mayor a cero
        if (amountEditText.text.toString().toFloat() <= 0){
            Toast.makeText(applicationContext,"La cantidad debe ser mayor a 0",Toast.LENGTH_SHORT).show()
        }
        else {

            val executor: ExecutorService = Executors.newSingleThreadExecutor()
            val amount = if(chargeType == 1) amountEditText.text.toString().toFloat()
            else -amountEditText.text.toString().toFloat()

            val charge = Charge( amount = amount,
                        category = category,
                        note = noteEditText.text.toString(),
                        date = dateTextView.text.toString(),
                        userName = username,
                        accountName = accountName )

            executor.execute(
                Runnable {
                    UserDatabase.getInstance(this)
                        ?.userDao
                        ?.insertCharge(charge)

                    Handler(Looper.getMainLooper()).post(Runnable {
                        if(chargeType == 1)
                            Toast.makeText(this, "Ingreso creado. ${amount} MXN a cuenta $accountName en categoría ${category}.", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this, "Egreso creado. -${amount} MXN a cuenta $accountName en categoría ${category}.", Toast.LENGTH_SHORT).show()
                        //("Egreso creado.", "-${amount} MXN a cuenta $accountName en categoría ${category}.")
                        finish()
                    })
                })
        }
    }

    private fun showDialog(title:String, message:String) {
        AlertDialog.Builder(this).setTitle(title).setMessage(message)
            .setPositiveButton("OK") { _, _ -> }.create().show()
    }
}
