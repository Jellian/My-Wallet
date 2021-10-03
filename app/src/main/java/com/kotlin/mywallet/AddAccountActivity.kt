package com.kotlin.mywallet

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.button.MaterialButton
import com.kotlin.mywallet.databinding.ActivityLoginBinding

class AddAccountActivity : AppCompatActivity() {

    private lateinit var accountNameEditText: EditText
    private lateinit var initialAmountEditText: EditText
    private lateinit var acceptButton: MaterialButton

    val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)

        acceptButton= findViewById(R.id.button_addAccount_accept)
        accountNameEditText = findViewById(R.id.textView_addAccount_accountName)
        initialAmountEditText = findViewById(R.id.textView_addAccount_initialAmount)

        val appBar = findViewById<Toolbar>(R.id.toolbar_addAccount_appBar)
        setSupportActionBar(appBar)

        binding.run{ acceptButton.setOnClickListener(returnNewAccountData())
        notificationTwo()
        }
        appBar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
    private fun notificationTwo() {
        val intent = Intent(this, GoalActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification = NotificationCompat.Builder(this, LoginActivity.CHANNEL_ANOUNCES)
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
    private fun returnNewAccountData() = View.OnClickListener {

        val accountList = intent.getSerializableExtra(ListActivity.ACCOUNT_LIST) as? ArrayList<*> //Lista de nombres de las cuentas)

        if(accountNameEditText.text.isNullOrEmpty() || initialAmountEditText.text.isNullOrEmpty()){
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
        }
        else if(accountList?.contains(accountNameEditText.text.toString())== true){
            showDialog("Espera...", "Ya existe una cuenta con este nombre.\nPor favor, elige otro nombre.")
        }
        else {
            val intent = Intent(this, AddAccountActivity::class.java)
            val amount = initialAmountEditText.text.toString()
            val name = accountNameEditText.text.toString()

            intent.putExtra(HomeActivity.NEW_ACCOUNT_NAME, name)
            intent.putExtra(HomeActivity.NEW_ACCOUNT_AMOUNT, amount)
            setResult(Activity.RESULT_OK, intent)
            Toast.makeText(this, "Cuenta $name agregada.", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun showDialog(title:String,message:String){
        AlertDialog.Builder(this).setTitle(title).setMessage(message)
            .setPositiveButton("OK"){ _, _ -> }.create().show()
    }
}