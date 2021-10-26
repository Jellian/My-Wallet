package com.kotlin.mywallet.login

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kotlin.mywallet.R
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.databinding.FragmentSignInBinding
import com.kotlin.mywallet.home.HomeActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        viewModel = SignInViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository, requireContext()
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            setNotificationChannel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignInAccept.setOnClickListener { checkSignInInfo() }
    }

    private fun checkSignInInfo(){

        if ( binding.editTextSignInUserName.text.isNullOrEmpty() )
            Toast.makeText(context, "Nombre de usuario vacío", Toast.LENGTH_SHORT).show()
        else {

            val executor: ExecutorService = Executors.newSingleThreadExecutor()

            executor.execute{
                val nameAndPassUsr = viewModel.getUserByNameAndPassword(
                    binding.editTextSignInUserName.text.toString(),
                    binding.editTextSignInPassword.text.toString()
                )
                val emailAndPassUsr = viewModel.getUserByEmailAndPassword(
                    binding.editTextSignInUserName.text.toString(),
                    binding.editTextSignInPassword.text.toString()
                )

                Handler(Looper.getMainLooper()).post{
                    when {
                        nameAndPassUsr != null -> {
                            notificationOne()
                            signIn(nameAndPassUsr.username, nameAndPassUsr.email.toString())
                        }
                        emailAndPassUsr != null -> {
                            notificationOne()
                            signIn(emailAndPassUsr.username, emailAndPassUsr.email.toString())
                        }
                        else -> { Toast.makeText(requireContext(), "Tu nombre de usuario, email y/o tu contraseña son incorrectos", Toast.LENGTH_SHORT).show() }
                    }
                }
            }
        }
    }

    //Creación del canal de anuncios de la app
    @RequiresApi(Build.VERSION_CODES.O)
    fun setNotificationChannel() {
        val name = "Anuncios My Wallet"
        val descriptionText = "Notificacion al iniciar sesión en MW"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(MainActivity.CHANNEL_ANNOUNCES, name, importance).apply { description = descriptionText }

        val notificationManager = activity?.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    //Primera notificación
    private fun notificationOne() {
        val notification = NotificationCompat.Builder(requireContext(), MainActivity.CHANNEL_ANNOUNCES)
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

    private fun signIn(userName: String, userEmail: String) {

        viewModel.editStringPref(MainActivity.IS_LOGGED, "TRUE")
        viewModel.editStringPref(MainActivity.USER_NAME, userName)
        viewModel.editStringPref(MainActivity.USER_EMAIL, userEmail)

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra(MainActivity.USER_NAME, userName)
        intent.putExtra(MainActivity.USER_EMAIL, userEmail)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}







