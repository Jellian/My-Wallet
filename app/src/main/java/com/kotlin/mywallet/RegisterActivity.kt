package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kotlin.mywallet.data.entities.User
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.databinding.ActivityMainBinding
import com.kotlin.mywallet.databinding.ActivityRegisterBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonRegisterAccept.setOnClickListener { signUp() }

    }

    private fun signUp(){

        with(binding){
            if(editTextRegisterUserName.text.isNullOrEmpty() || editTextRegisterEmail.text.isNullOrEmpty() || editTextRegisterPassword.text.isNullOrEmpty())
                Toast.makeText(applicationContext, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            else if(editTextRegisterPassword.text.toString().length < 8)
                Toast.makeText(applicationContext, "La contraseña debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show()
            else if(!isEmailValid(editTextRegisterEmail.text.toString()))
                Toast.makeText(applicationContext, "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show()
            else{
                executeDbProcess()
                val intent = Intent(applicationContext, RegisterActivity::class.java)

                intent.putExtra(MainActivity.USER_NAME, binding.editTextRegisterUserName.text.toString())
                intent.putExtra(MainActivity.USER_EMAIL, binding.editTextRegisterEmail.text.toString())
                intent.putExtra(MainActivity.USER_PASSWORD, binding.editTextRegisterPassword.text.toString())

                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

//
//        if(binding.editTextRegisterUserName.text.isNullOrEmpty() || binding.editTextRegisterEmail.text.isNullOrEmpty() || binding.editTextRegisterPassword.text.isNullOrEmpty())
//            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
//        else if (!isEmailValid(binding.editTextRegisterEmail.text.toString()))
//            Toast.makeText(this, "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show()
//        else if (binding.editTextRegisterPassword.text.toString().length < 8)
//            Toast.makeText(this, "La contraseña debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show()
//        else {
//            executeDbProcess()
//            val intent = Intent(this, RegisterActivity::class.java)
//
//            intent.putExtra(MainActivity.USER_NAME, binding.editTextRegisterUserName.text.toString())
//            intent.putExtra(MainActivity.USER_EMAIL, binding.editTextRegisterEmail.text.toString())
//            intent.putExtra(MainActivity.USER_PASSWORD, binding.editTextRegisterPassword.text.toString())
//
//            setResult(Activity.RESULT_OK, intent)
//            finish()
//        }
    }

    private fun executeDbProcess(){

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(
            Runnable {

                /*val db = Room.databaseBuilder(
                    applicationContext,
                    UserDb::class.java, "MyWallet_DB"
                ).build()
                val userDao = db.userDao()*/

                UserDatabase.getInstance(context = applicationContext)
                    ?.userDao
                    ?.insertAll(
                        User(
                            email = binding.editTextRegisterEmail.text.toString(),
                            password = binding.editTextRegisterPassword.text.toString(),
                            userName = binding.editTextRegisterUserName.text.toString()
                        )
                    )
                Handler(Looper.getMainLooper()).post(Runnable {
                    Toast.makeText(this, "Has sido correctamente registrado", Toast.LENGTH_LONG).show()

                })


                //val users: List<User> = userDao.getAll()
            })


        /*
                    auth.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                //Log.d(TAG, "createUserWithEmail:success")
                                Toast.makeText(this, "Has sido correctamente registrado", Toast.LENGTH_LONG).show()
                                //updateUI(user, null)
                            } else {
                                //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(this, "Nope", Toast.LENGTH_LONG).show()
                            }
                        }
    */


    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}