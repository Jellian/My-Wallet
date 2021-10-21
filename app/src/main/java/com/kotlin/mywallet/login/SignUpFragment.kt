package com.kotlin.mywallet.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kotlin.mywallet.R
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.data.entities.User
import com.kotlin.mywallet.databinding.FragmentSignUpBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        binding.buttonSignUpAccept.setOnClickListener { signUp() }

        return binding.root
    }

    private fun signUp() {

        with(binding) {

            if (editTextSignUpUserName.text.isNullOrEmpty() || editTextSignUpEmail.text.isNullOrEmpty() || editTextSignUpPassword.text.isNullOrEmpty())
                Toast.makeText(context, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            else if (editTextSignUpPassword.text.toString().length < 8)
                Toast.makeText(context, "La contraseña debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show()
            else if (!isEmailValid(editTextSignUpEmail.text.toString()))
                Toast.makeText(context, "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show()
            else {
                executeDbProcess()
                auth()
                findNavController().navigate(R.id.mainFragment, null, null)
            }
        }

    }
    private fun executeDbProcess(){

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(
            Runnable {
                UserDatabase.getInstance(requireContext())
                    ?.userDao
                    ?.insertAll(
                        User(
                            email = binding.editTextSignUpEmail.text.toString(),
                            password = binding.editTextSignUpPassword.text.toString(),
                            userName = binding.editTextSignUpUserName.text.toString()
                        )
                    )
                Handler(Looper.getMainLooper()).post(Runnable {
                    //Toast.makeText(context, "Has sido correctamente registrado", Toast.LENGTH_LONG).show()
                })
            })
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun auth(){
        auth.createUserWithEmailAndPassword(

            binding.editTextSignUpEmail.text.toString(),
            binding.editTextSignUpPassword.text.toString()

        ).addOnCompleteListener( requireActivity() ) { task ->
                if (task.isSuccessful) {
                    //Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(context, "Has sido correctamente registrado", Toast.LENGTH_LONG).show()
                    //updateUI(user, null)
                } else {
                    //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Nope", Toast.LENGTH_LONG).show()
                }
            }

    }
}
