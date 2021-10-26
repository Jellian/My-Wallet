package com.kotlin.mywallet.login

import android.animation.AnimatorInflater
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.kotlin.mywallet.R
import com.kotlin.mywallet.databinding.FragmentMainBinding
import com.kotlin.mywallet.home.HomeActivity

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)

        preferences = activity?.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE) as SharedPreferences

        FirebaseApp.initializeApp( requireContext() )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateIcon()

        binding.buttonMainSignUp.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment, null, MainActivity.options)
        }

        binding.buttonMainSignIn.setOnClickListener {
            findNavController().navigate(R.id.signInFragment, null, MainActivity.options)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener( OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Error", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result
            Log.d("FCM_TOKEN",token!!)
            //Toast.makeText(baseContext,"FCM token: $token", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        if( isLogged() ){ goToHome() }
    }

    private fun isLogged(): Boolean {
        val isLogged = preferences.getString(MainActivity.IS_LOGGED, "")
        return isLogged == "TRUE"
    }

    private fun goToHome(){
        val userName = preferences.getString(MainActivity.USER_NAME, "").toString()
        val userEmail = preferences.getString(MainActivity.USER_EMAIL, "").toString()

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra(MainActivity.USER_NAME, userName)
        intent.putExtra(MainActivity.USER_EMAIL, userEmail)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        animateIcon()
    }

    private fun animateIcon(){
        AnimatorInflater.loadAnimator(context, R.animator.bouncing).apply {
            setTarget(binding.imageViewSignUpAppIcon)
            start()
        }
    }
}