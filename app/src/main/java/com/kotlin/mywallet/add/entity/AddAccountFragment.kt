package com.kotlin.mywallet.add.entity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkBuilder
import com.kotlin.mywallet.R
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.databinding.FragmentAddAccountBinding
import com.kotlin.mywallet.home.HomeActivity
import com.kotlin.mywallet.login.MainActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddAccountFragment : Fragment() {

    private lateinit var binding: FragmentAddAccountBinding
    private lateinit var parentActivity: AddEntityActivity
    private lateinit var viewModel: AddAccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddAccountBinding.inflate(inflater, container, false)

        parentActivity = activity as AddEntityActivity
        notificationTwo()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBar = binding.toolbarAddAccountAppBar
        parentActivity.setSupportActionBar(appBar)

        viewModel = AddAccountViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository
        )

        if(parentActivity.isEditMode())
            setupEditMode()

        appBar.setNavigationOnClickListener { parentActivity.finish() }

        binding.buttonAddAccountAccept.setOnClickListener( checkNewAccountInfo() )

    }

    private fun checkNewAccountInfo() = View.OnClickListener {

        if(binding.textViewAddAccountAccountName.text.isNullOrEmpty() || binding.textViewAddAccountInitialAmount.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
        }

        else{

            val username = parentActivity.intent.getStringExtra(MainActivity.USER_NAME).toString()
            val newAccountName = binding.textViewAddAccountAccountName.text.toString()

            var accountToEdit: Account? = null

            val executor: ExecutorService = Executors.newSingleThreadExecutor()
            executor.execute {

                val accountNameList = viewModel.getAccountNamesByUser(username)

                if (parentActivity.isEditMode()) {
                    accountToEdit = viewModel.getAccountById(parentActivity.intent.getIntExtra(MainActivity.ID, 0))
                }

                Handler(Looper.getMainLooper()).post {

                    if (accountNameList.contains(newAccountName)) {
                        if(parentActivity.isEditMode() && accountToEdit?.accountName == newAccountName ) {
                            editAccount(accountToEdit)
                        }
                        else {
                            parentActivity.showDialog(
                                "Espera...",
                                "Ya existe una cuenta con este nombre.\nPor favor, elige otro nombre."
                            )
                        }
                    }
                    else if(parentActivity.isEditMode()){
                        editAccount(accountToEdit)
                    }
                    else
                        addAccount(username)
                }
            }
        }
    }

    fun addAccount(username: String){

        val name = binding.textViewAddAccountAccountName.text.toString()
        val amount = binding.textViewAddAccountInitialAmount.text.toString()

        val account = Account(accountName = name, initialAmount = amount.toFloat(), username = username)

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute {
            viewModel.insertAccount(account)

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Cuenta $name agregada.", Toast.LENGTH_SHORT).show()
                //parentActivity.showDialog("¡Genial!", "Cuenta $name agregada.")
                parentActivity.finish()
            }
        }
    }

    private fun editAccount(accountToEdit: Account?){

        if(accountToEdit != null ) {

            val name = binding.textViewAddAccountAccountName.text.toString()
            val amount = binding.textViewAddAccountInitialAmount.text.toString()

            val editedAccount = Account(
                id = accountToEdit.id,
                accountName = name,
                initialAmount = amount.toFloat(),
                username = accountToEdit.username
            )

            val executor: ExecutorService = Executors.newSingleThreadExecutor()

            executor.execute {
                viewModel.updateAccountById(accountToEdit, editedAccount)

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Cuenta $name editada.", Toast.LENGTH_SHORT).show()
                    //parentActivity.showDialog("¡Genial!", "Cuenta $name agregada.")
                    parentActivity.finish()
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun notificationTwo() {

        val pendingIntent = NavDeepLinkBuilder(requireContext())
            .setComponentName(HomeActivity::class.java)
            .setGraph(R.navigation.home_navigation)
            .setDestination(R.id.goalFragment)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(requireContext(), MainActivity.CHANNEL_ANNOUNCES)
            .setSmallIcon(R.drawable.wallet3)
            .setColor(ContextCompat.getColor(requireContext(), R.color.teal_200))
            .setContentTitle(getString(R.string.nombre_canal))
            .setContentText(getString(R.string.notificacion1))
            .setLargeIcon(parentActivity.getDrawable(R.drawable.walleticon)?.toBitmap())
            .setStyle(NotificationCompat.BigTextStyle().bigText(getString(R.string.notificacion2)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(requireContext())) {
            notify(22, notification)
        }
    }

    private fun setupEditMode(){

        binding.textViewAddAccountNewAccount.text = "Editar cuenta"
        binding.toolbarAddAccountAppBar.title = "Editar cuenta"

        val accountId = parentActivity.intent.getIntExtra(MainActivity.ID, 0)
        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        var existingAccount: Account? = null

        executor.execute {

            if (accountId != 0) {
                existingAccount = viewModel.getAccountById(accountId)
            }

            Handler(Looper.getMainLooper()).post {

                binding.textViewAddAccountAccountName.setText(existingAccount?.accountName)
                binding.textViewAddAccountInitialAmount.setText(existingAccount?.initialAmount.toString())

            }
        }
    }

}