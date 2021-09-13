package com.kotlin.mywallet

import android.animation.AnimatorInflater
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import com.google.android.material.button.MaterialButton

class MainActivity : Activity() {  // Extends Activity y no AppCompatActivity, para ocultar barra de titulo

    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_EMAIL = "USER_EMAIL"
        const val USER_PASSWORD = "USER_PASSWORD"
    }

    private lateinit var preferences: SharedPreferences

    private lateinit var signUpButton: MaterialButton
    private lateinit var signInButton: MaterialButton
    private lateinit var iconImageView: ImageView

    private var userName: String = ""
    private var userEmail: String = ""
    private var userPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        preferences = getSharedPreferences(HomeActivity.PREFS_NAME, Context.MODE_PRIVATE)

        iconImageView= findViewById(R.id.imageView_register_appIcon)
        signUpButton= findViewById(R.id.button_main_signUp)
        signInButton= findViewById(R.id.button_main_signIn)

        animateIcon()

        signUpButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, 1)
        }

        signInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(USER_NAME, userName)
            intent.putExtra(USER_EMAIL, userEmail)
            intent.putExtra(USER_PASSWORD, userPassword)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        if( isLogged() ){ goToHome() }
    }

    private fun goToHome(){
        userName = preferences.getString(HomeActivity.USER_NAME, "").toString()
        userEmail = preferences.getString(HomeActivity.USER_EMAIL, "").toString()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        animateIcon()
        if(resultCode == RESULT_OK && data!= null)
            userName = data.getStringExtra(USER_NAME).toString()
            userEmail = data?.getStringExtra(USER_EMAIL).toString()
            userPassword = data?.getStringExtra(USER_PASSWORD).toString()
    }

    override fun onResume() {
        super.onResume()
        animateIcon()
    }

    private fun animateIcon(){
        AnimatorInflater.loadAnimator(this, R.animator.bouncing).apply {
            setTarget(iconImageView)
            start()
        }
    }
}