package com.kotlin.mywallet.add.entity

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.kotlin.mywallet.login.MainActivity
import com.kotlin.mywallet.utils.Categories
import kotlinx.android.synthetic.main.fragment_detail.*
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
    private var date: String = ""

    private lateinit var categories: List<String>

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

        if (parentActivity.isEditMode())    setupEditMode()
        else    setupAddMode()

        binding.buttonAddChargeAdd.setOnClickListener { checkNewChargeInfo() }
        appBar.setNavigationOnClickListener { parentActivity.finish() }

    }

    private fun checkNewChargeInfo() {

        if ( binding.editTextAddChargeAmount.text.toString().toFloat() <= 0){
            Toast.makeText( context ,"La cantidad debe ser mayor a 0",Toast.LENGTH_SHORT).show()
        }
        else {
            if (parentActivity.isEditMode()) {
                var chargeToEdit: Charge?

                val executor: ExecutorService = Executors.newSingleThreadExecutor()
                executor.execute {

                    chargeToEdit = viewModel.getChargeById( parentActivity.intent.getIntExtra( MainActivity.ID, 0) )

                    Handler(Looper.getMainLooper()).post {
                        editCharge(chargeToEdit)
                    }
                }
            }
            else
                addCharge()
        }
    }

    private fun addCharge(){

        var amount = binding.editTextAddChargeAmount.text.toString().toFloat()

        if(chargeType != 1) amount = -amount

        val charge = Charge( amount = amount, category = category,
            note = binding.editTextAddChargeNote.text.toString(),
            date = date,
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
                parentActivity.finish()
            }
        }
    }

    private fun editCharge(chargeToEdit: Charge?){

        var amount = binding.editTextAddChargeAmount.text.toString().toFloat()

        if(chargeType != 1) amount = -amount

        if(chargeToEdit != null ) {

            val editedCharge = Charge(
                id = chargeToEdit.id, amount = amount, category= category,
                note = binding.editTextAddChargeNote.text.toString(),
                date = date,
                accountName = chargeToEdit.accountName,
                username = chargeToEdit.username
            )

            if(chargeToEdit.amount == editedCharge.amount
                && chargeToEdit.category == editedCharge.category
                && chargeToEdit.date == editedCharge.date
                && chargeToEdit.note == editedCharge.note){
                Toast.makeText(context, "Nada ha cambiado. Ningún campo fue modificado.", Toast.LENGTH_SHORT).show()
            }
            else {
                val executor: ExecutorService = Executors.newSingleThreadExecutor()
                executor.execute {

                    viewModel.updateChargeById(chargeToEdit, editedCharge)

                    Handler(Looper.getMainLooper()).post {
                        if (chargeType == 1)
                        //parentActivity.showDialog("Ingreso creado.", "-${amount} MXN a cuenta $accountName en categoría ${category}.")
                            Toast.makeText(
                                context,
                                "Ingreso editado. ${amount} MXN a cuenta $accountName en categoría ${category}.",
                                Toast.LENGTH_SHORT
                            ).show()
                        else
                            Toast.makeText(
                                context,
                                "Egreso editado. -${amount} MXN a cuenta $accountName en categoría ${category}.",
                                Toast.LENGTH_SHORT
                            ).show()
                        parentActivity.finish()
                    }
                }
            }
        }
    }

    private fun setupEditMode(){

        val accountId = parentActivity.intent.getIntExtra(MainActivity.ID, 0)
        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        var existingCharge: Charge? = null
        var accountNameList: List<String>? = null

        executor.execute {

            if (accountId != 0) {
                existingCharge = viewModel.getChargeById(accountId)
                username = existingCharge?.username.toString()
                accountNameList = viewModel.getAccountNamesByUser(username)
            }

            Handler(Looper.getMainLooper()).post {
                if(existingCharge != null) {

                    if (existingCharge?.amount!! < 0) {
                        chargeType = -1
                        categories = Categories.expendOptions
                        binding.textViewAddChargeHeaderAction.text = "Editar egreso"
                        binding.toolbarAddChargeAppBar.title = "Editar egreso"
                        binding.editTextAddChargeAmount.setText((-1f*(existingCharge?.amount!!)).toString())
                    }
                    else{
                        chargeType = 1
                        categories = Categories.incomeOptions
                        binding.textViewAddChargeHeaderAction.text = "Editar ingreso"
                        binding.toolbarAddChargeAppBar.title = "Editar ingreso"
                        binding.editTextAddChargeAmount.setText((existingCharge?.amount).toString())
                    }
                    binding.editTextAddChargeNote.setText(existingCharge?.note)

                    setupAccountsSpinner(accountNameList?.indexOf(existingCharge?.accountName))
                    setupCategoriesSpinner(categories.indexOf(existingCharge?.category))
                    setupDatePickerDialog(existingCharge?.date.toString())
                }
            }
        }
    }

    private fun setupAddMode(){

        chargeType = parentActivity.intent.getIntExtra(MainActivity.TYPE, 0)
        username = parentActivity.intent.getStringExtra(MainActivity.USER_NAME).toString()
        accountName = parentActivity.intent.getStringExtra(MainActivity.ACCOUNT).toString()

        if (chargeType != +1) {
            categories = Categories.expendOptions
            binding.textViewAddChargeHeaderAction.text = getString(R.string.anadir_egreso)
        }
        else{
            categories = Categories.incomeOptions
            binding.textViewAddChargeHeaderAction.text = getString(R.string.anadir_ingreso)
        }

        setupAccountsSpinner()
        setupCategoriesSpinner()
        setupDatePickerDialog()
    }

    private fun setupCategoriesSpinner(defaultValue: Int? = 0){

        binding.spinnerAddChargeCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = categories[position]
            }
        }

        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAddChargeCategories.adapter = categoryAdapter
        if (defaultValue != null)
            binding.spinnerAddChargeCategories.setSelection(defaultValue)
    }

    private fun setupAccountsSpinner(defaultValue: Int? = 0) {

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute{

            val accountNameList = viewModel.getAccountNamesByUser(username)

            Handler(Looper.getMainLooper()).post {

                val accountAdapter = ArrayAdapter( requireContext() , android.R.layout.simple_spinner_item, accountNameList.toList())
                accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerAddChargeAccounts.adapter = accountAdapter

                if (defaultValue != null)
                    binding.spinnerAddChargeAccounts.setSelection(defaultValue)

                if(accountName.isNotEmpty())
                    binding.spinnerAddChargeAccounts.setSelection(accountNameList.indexOf(accountName))

                binding.spinnerAddChargeAccounts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) { }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        accountName = accountNameList[position]
                    }
                }
            }

        }
    }

    private fun setupDatePickerDialog( existingDate: String? = null ){

        val actualYear: Int
        val actualMonth: Int
        val dayOfMonth: Int

        if (existingDate.isNullOrEmpty()){
            val calendar: Calendar = Calendar.getInstance()
            actualYear = calendar.get(Calendar.YEAR)
            actualMonth = calendar.get(Calendar.MONTH)
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        }
        else{
            val dateList = existingDate.split("/ ")
            actualYear = dateList[0].toInt()
            actualMonth = dateList[1].toInt()-1
            dayOfMonth = dateList[2].toInt()
        }

        binding.textViewAddChargeDate.text = ("$dayOfMonth/ ${actualMonth + 1}/ $actualYear")

        date = "$actualYear/ ${String.format("%02d", actualMonth + 1)}/ ${String.format("%02d", dayOfMonth)}"

        binding.textViewAddChargeDate.setOnClickListener {
            val dateListener = { _: DatePicker, year: Int, month: Int, day: Int ->
                binding.textViewAddChargeDate.text = ("$day/ ${month + 1}/ $year")

                date = "$year/ ${String.format("%02d", month + 1)}/ ${String.format("%02d", day)}"
            }

            val datePickerDialog = DatePickerDialog( requireContext(), dateListener, actualYear, actualMonth, dayOfMonth )
            datePickerDialog.show()
        }

    }
}