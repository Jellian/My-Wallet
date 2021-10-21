package com.kotlin.mywallet

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.databinding.ActivityAddAccountBinding
import com.kotlin.mywallet.login.MainActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appBar = findViewById<Toolbar>(R.id.toolbar_addAccount_appBar)
        setSupportActionBar(appBar)

        binding.buttonAddAccountAccept.setOnClickListener(checkNewAccountInfo())

        notificationTwo()

        appBar.setNavigationOnClickListener { finish() }

    }

    private fun checkNewAccountInfo() = View.OnClickListener {

        if(binding.textViewAddAccountAccountName.text.isNullOrEmpty() || binding.textViewAddAccountInitialAmount.text.isNullOrEmpty()){
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
        }

        else{
            val username = intent.getStringExtra(HomeActivity.USER_NAME).toString()

            val executor: ExecutorService = Executors.newSingleThreadExecutor()
            executor.execute(
                Runnable {

                    val accountNameList = UserDatabase.getInstance(this)
                        ?.userDao
                        ?.findAccountNamesByUser(username)

                    Handler(Looper.getMainLooper()).post(Runnable {

                        if (accountNameList?.contains(binding.textViewAddAccountAccountName.text.toString()) == true)
                            showDialog("Espera...", "Ya existe una cuenta con este nombre.\nPor favor, elige otro nombre.")
                        else
                            addAccount(username)
                    })
                })
        }
    }

    fun addAccount(username: String){

        val amount = binding.textViewAddAccountInitialAmount.text.toString()
        val name = binding.textViewAddAccountAccountName.text.toString()

        val account = Account(name, amount.toFloat(), username)

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(
            Runnable {
                UserDatabase.getInstance(this)
                    ?.userDao
                    ?.insertAccount(account)

                Handler(Looper.getMainLooper()).post(Runnable {
                    Toast.makeText(this, "Cuenta $name agregada.", Toast.LENGTH_SHORT).show()
                    finish()
                })
            })
    }

    private fun showDialog(title:String,message:String){
        AlertDialog.Builder(this).setTitle(title).setMessage(message)
            .setPositiveButton("OK"){ _, _ -> }.create().show()
    }

    private fun notificationTwo() {
        val intent = Intent(this, GoalActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification = NotificationCompat.Builder(this, MainActivity.CHANNEL_ANNOUNCES)
            .setSmallIcon(R.drawable.wallet3)
            .setColor(ContextCompat.getColor(this, R.color.teal_200))
            .setContentTitle(getString(R.string.nombre_canal))
            .setContentText(getString(R.string.notificacion1))
            .setLargeIcon(getDrawable(R.drawable.walleticon)?.toBitmap())
            .setStyle(NotificationCompat.BigTextStyle().bigText(getString(R.string.notificacion2)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(22, notification)
        }
    }
}