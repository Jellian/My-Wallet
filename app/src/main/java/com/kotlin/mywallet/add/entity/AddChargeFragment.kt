package com.kotlin.mywallet.add.entity

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.kotlin.mywallet.R
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.data.entities.Charge
import com.kotlin.mywallet.databinding.FragmentAddChargeBinding
import com.kotlin.mywallet.databinding.FragmentHomeBinding
import com.kotlin.mywallet.home.HomeActivity
import com.kotlin.mywallet.utils.Categories
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddChargeFragment : Fragment() {

    private lateinit var binding: FragmentAddChargeBinding
    private lateinit var parentActivity: AddEntityActivity
    private lateinit var viewModel: AddChargeViewModel

    private var chargeType: Int? = 0
    private var username: String = ""
    private var category: String = ""
    private var accountName: String = ""

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View {
        binding = FragmentAddChargeBinding.inflate(inflater, container, false)

        parentActivity = activity as AddEntityActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBar = binding.toolbarAddChargeAppBar
        parentActivity.setSupportActionBar(appBar)

        viewModel = AddChargeViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository
        )

        val calendar: Calendar = Calendar.getInstance()
        val actualYear: Int = calendar.get(Calendar.YEAR)
        val actualMonth: Int = calendar.get(Calendar.MONTH)
        val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)

        "${actualMonth+1}/ $dayOfMonth/ $actualYear".also { binding.textViewAddChargeDate.text = it }

        chargeType = parentActivity.intent.getIntExtra(HomeActivity.TYPE, 0)
        username = parentActivity.intent.getStringExtra(HomeActivity.USER_NAME).toString()

        val categories : List<String>

        getAccounts()

        with(binding) {
            if (chargeType != +1) {
                categories = Categories.expendOptions
                textViewAddChargeHeaderAction.text = getString(R.string.anadir_egreso)
            } else {
                categories = Categories.incomeOptions
                textViewAddChargeHeaderAction.text = getString(R.string.anadir_ingreso)
            }

            buttonAddChargeAdd.setOnClickListener { createCharge() }

            textViewAddChargeDate.setOnClickListener {
                val dateListener = { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                    textViewAddChargeDate.text = ("$day/ ${month + 1}/ $year")
                }

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    dateListener,
                    actualYear,
                    actualMonth,
                    dayOfMonth
                )
                datePickerDialog.show()
            }

            spinnerAddChargeCategories.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        category = categories[position]
                    }
                }

            val categoryAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerAddChargeCategories.adapter = categoryAdapter
        }

        appBar.setNavigationOnClickListener { parentActivity.finishActivity() }

    }

    private fun getAccounts() {

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute{

            val accountNameList = viewModel.getAccountNamesByUser(username)

            Handler(Looper.getMainLooper()).post {

                val accountAdapter = ArrayAdapter( requireContext() , android.R.layout.simple_spinner_item, accountNameList.toList())
                accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerAddChargeAccounts.adapter = accountAdapter

                binding.spinnerAddChargeAccounts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) { }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        accountName = accountNameList[position]
                    }
                }
            }
        }
    }

    private fun createCharge() {

        var amount = binding.editTextAddChargeAmount.text.toString().toFloat()


        if ( amount <= 0){
            Toast.makeText( context ,"La cantidad debe ser mayor a 0",Toast.LENGTH_SHORT).show()
        }
        else {

            if(chargeType != 1) amount = -amount

            val charge = Charge( amount = amount, category = category,
                note = binding.editTextAddChargeNote.text.toString(),
                date = binding.textViewAddChargeDate.text.toString(),
                username = username, accountName = accountName )

            val executor: ExecutorService = Executors.newSingleThreadExecutor()

            executor.execute {

                viewModel.insertCharge(charge)

                Handler(Looper.getMainLooper()).post {
                    if(chargeType == 1)
                        //parentActivity.showDialog("Ingreso creado.", "-${amount} MXN a cuenta $accountName en categoría ${category}.")
                        Toast.makeText(context, "Ingreso creado. ${amount} MXN a cuenta $accountName en categoría ${category}.", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "Egreso creado. -${amount} MXN a cuenta $accountName en categoría ${category}.", Toast.LENGTH_SHORT).show()
                    parentActivity.finishActivity()
                }
            }
        }
    }



}