package com.kotlin.mywallet.add.entity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

        appBar.setNavigationOnClickListener { parentActivity.finishActivity() }

        binding.buttonAddAccountAccept.setOnClickListener( checkNewAccountInfo() )

    }

    private fun checkNewAccountInfo() = View.OnClickListener {

        if(binding.textViewAddAccountAccountName.text.isNullOrEmpty() || binding.textViewAddAccountInitialAmount.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
        }

        else{
            val username = parentActivity.intent.getStringExtra(HomeActivity.USER_NAME).toString()

            val executor: ExecutorService = Executors.newSingleThreadExecutor()
            executor.execute {

                val accountNameList = viewModel.getAccountNamesByUser(username)

                accountNameList.forEach{ Log.d("ACCOUNT NAME", it)}

                Handler(Looper.getMainLooper()).post{
                        if (accountNameList.contains(binding.textViewAddAccountAccountName.text.toString()))
                            parentActivity.showDialog("Espera...", "Ya existe una cuenta con este nombre.\nPor favor, elige otro nombre.")
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
                parentActivity.finishActivity()
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

}