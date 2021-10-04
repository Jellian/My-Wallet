package com.kotlin.mywallet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.kotlin.mywallet.data.UserDb
import com.kotlin.mywallet.databinding.ActivityLoginBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LoginActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    private lateinit var signInButton: MaterialButton
    private lateinit var passwordEditText: EditText
    private lateinit var userNameEditText: EditText

    companion object {
        const val CHANNEL_ANOUNCES = "CHANNEL_ANOUNCES"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }

        preferences = getSharedPreferences(HomeActivity.PREFS_NAME, Context.MODE_PRIVATE)

        signInButton = findViewById(R.id.button_register_accept)
        userNameEditText = findViewById(R.id.editText_register_userName)
        passwordEditText = findViewById(R.id.editText_register_password)


        //Se agrega el binding para llamada a la notificación
        val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate((layoutInflater)) }

        binding.run {
            signInButton.setOnClickListener {
                if (userNameEditText.text.isNullOrEmpty())
                    Toast.makeText(
                        this@LoginActivity,
                        "Nombre de usuario vacío",
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    checkDatabase()
            }
        }
    }


    fun checkDatabase() {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(
            Runnable {
                val emailAndPass = UserDb.getInstance(context = applicationContext)
                    ?.userDao()
                    ?.findByEmailAndPassword(
                        userNameEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                val userAndPass = UserDb.getInstance(context = applicationContext)
                    ?.userDao()
                    ?.findByUsernameAndPassword(
                        userNameEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                Handler(Looper.getMainLooper()).post(Runnable {
                    if (emailAndPass != null) {
                        logIn(emailAndPass.user_name, emailAndPass.email, emailAndPass.password)
                        notificationOne()

                    } else if (userAndPass != null) {
                        logIn(userAndPass.user_name, userAndPass.email, userAndPass.password)
                        notificationOne()

                    } else {
                        Toast.makeText(this, "Tu username/email y/o tu password son incorrectos", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        )
    }
//CREACIÓN DEL CANAL DE ANUNCIOS DE LA APLICACIÓN

    @RequiresApi(Build.VERSION_CODES.O)
    fun setNotificationChannel() {
        val name = "Anuncios My Wallet"
        val descriptionText = "Notificacion al iniciar sesión en MW"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ANOUNCES, name, importance).apply {
            description = descriptionText
        }
        /*
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(CHANNEL_ANOUNCES, name, importance).apply {
                description = descriptionText
            }
        } else {
            null
        }*/

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    //PRIMERA NOTIFICACION
    private fun notificationOne() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ANOUNCES)
            .setSmallIcon(R.drawable.wallet3)
            .setColor(ContextCompat.getColor(this, R.color.primaryColor))
            .setContentTitle(getString(R.string.nombrenotificacion))
            .setContentText(getString(R.string.notificacion1))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(20, notification)
        }
    }


    private fun logIn(userName: String?, userEmail: String?, userPassword: String?) {
        preferences.edit().putString(HomeActivity.IS_LOGGED, "TRUE").apply()
        preferences.edit().putString(HomeActivity.USER_NAME, userName).apply()
        preferences.edit().putString(HomeActivity.USER_EMAIL, userEmail).apply()

        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(MainActivity.USER_NAME, userName)
        intent.putExtra(MainActivity.USER_EMAIL, userEmail)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }
}