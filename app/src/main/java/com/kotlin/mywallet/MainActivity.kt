package com.kotlin.mywallet

import android.animation.AnimatorInflater
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseApp.getInstance
import java.util.Calendar.getInstance
import com.google.firebase.messaging.FirebaseMessaging
import com.kotlin.mywallet.databinding.ActivityHomeBinding
import com.kotlin.mywallet.databinding.ActivityMainBinding


class MainActivity : Activity() {  // Extends Activity y no AppCompatActivity, para ocultar barra de titulo

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_EMAIL = "USER_EMAIL"
        const val USER_PASSWORD = "USER_PASSWORD"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(HomeActivity.PREFS_NAME, Context.MODE_PRIVATE)

        FirebaseApp.initializeApp(this)

        animateIcon()

        binding.buttonMainSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMainSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Error", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            //Log.d("FCM_TOKEN",token!!)
            //Toast.makeText(baseContext,"FCM token: $token", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        if( isLogged() ){ goToHome() }
    }

    private fun goToHome(){
        val userName = preferences.getString(HomeActivity.USER_NAME, "").toString()
        val userEmail = preferences.getString(HomeActivity.USER_EMAIL, "").toString()

        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(USER_NAME, userName)
        intent.putExtra(USER_EMAIL, userEmail)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun isLogged(): Boolean {
        val isLogged = preferences.getString(HomeActivity.IS_LOGGED, "")
        return isLogged == "TRUE"
    }

    override fun onResume() {
        super.onResume()
        animateIcon()
    }

    private fun animateIcon(){
        AnimatorInflater.loadAnimator(this, R.animator.bouncing).apply {
            setTarget(binding.imageViewRegisterAppIcon)
            start()
        }
    }

}