package com.kotlin.mywallet

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.databinding.FragmentSignInBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SignInFragment : Fragment() {

    companion object {
        const val CHANNEL_ANOUNCES = "CHANNEL_ANOUNCES"
    }

    private lateinit var preferences: SharedPreferences
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }

        preferences = activity?.getSharedPreferences(HomeActivity.PREFS_NAME, Context.MODE_PRIVATE) as SharedPreferences

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignInAccept.setOnClickListener {
            if (binding.editTextSignInUserName.text.isNullOrEmpty())
                Toast.makeText(context, "Nombre de usuario vacío", Toast.LENGTH_SHORT).show()
            else checkDatabase()
        }
    }

    private fun checkDatabase() {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(
            Runnable {
                val emailAndPass = UserDatabase.getInstance(requireContext())
                    ?.userDao
                    ?.findByEmailAndPassword(
                        binding.editTextSignInUserName.text.toString(),
                        binding.editTextSignInPassword.text.toString()
                    )
                val userAndPass = UserDatabase.getInstance(requireContext())
                    ?.userDao
                    ?.findByUsernameAndPassword(
                        binding.editTextSignInUserName.text.toString(),
                        binding.editTextSignInPassword.text.toString()
                    )
                Handler(Looper.getMainLooper()).post(Runnable {
                    when {
                        emailAndPass != null -> {
                            signIn(emailAndPass.userName, emailAndPass.email, emailAndPass.password)
                            notificationOne()
                        }
                        userAndPass != null -> {
                            signIn(userAndPass.userName, userAndPass.email, userAndPass.password)
                            notificationOne()
                        }
                        else -> { Toast.makeText(requireContext(), "Tu username/email y/o tu password son incorrectos", Toast.LENGTH_SHORT).show() }
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
        val channel = NotificationChannel(CHANNEL_ANOUNCES, name, importance).apply { description = descriptionText }

        val notificationManager = activity?.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    //PRIMERA NOTIFICACION
    private fun notificationOne() {
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ANOUNCES)
            .setSmallIcon(R.drawable.wallet3)
            .setColor(ContextCompat.getColor(requireContext(), R.color.primaryColor))
            .setContentTitle(getString(R.string.nombrenotificacion))
            .setContentText(getString(R.string.notificacion1))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(requireContext())) {
            notify(20, notification)
        }
    }

    private fun signIn(userName: String?, userEmail: String?, userPassword: String?) {
        preferences.edit().putString(HomeActivity.IS_LOGGED, "TRUE").apply()
        preferences.edit().putString(HomeActivity.USER_NAME, userName).apply()
        preferences.edit().putString(HomeActivity.USER_EMAIL, userEmail).apply()

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra(MainActivity.USER_NAME, userName)
        intent.putExtra(MainActivity.USER_EMAIL, userEmail)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}







