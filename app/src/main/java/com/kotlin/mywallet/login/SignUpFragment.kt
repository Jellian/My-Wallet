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
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.data.entities.User
import com.kotlin.mywallet.databinding.FragmentSignUpBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        viewModel = SignUpViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository
        )

        auth = Firebase.auth

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignUpAccept.setOnClickListener { checkSignUpInfo() }
    }

    private fun checkSignUpInfo() {

        with(binding) {

            if (editTextSignUpUserName.text.isNullOrEmpty() || editTextSignUpEmail.text.isNullOrEmpty() || editTextSignUpPassword.text.isNullOrEmpty())
                Toast.makeText(context, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            else if (editTextSignUpPassword.text.toString().length < 8)
                Toast.makeText(context, "La contraseña debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show()
            else if (!isEmailValid(editTextSignUpEmail.text.toString()))
                Toast.makeText(context, "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show()
            else {
                signUp()
            }
        }

    }
    private fun signUp(){

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute {
            viewModel.insertUser(
                User(
                    username = binding.editTextSignUpUserName.text.toString(),
                    password = binding.editTextSignUpPassword.text.toString(),
                    email = binding.editTextSignUpEmail.text.toString()
                )
            )

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Has sido correctamente registrado", Toast.LENGTH_LONG).show()
                auth()
                findNavController().navigate(R.id.mainFragment, null, null)
            }
        }
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
                    Toast.makeText(context, "Ha ocurrido un problema, intenta de nuevo con otro correo", Toast.LENGTH_LONG).show()
                }
            }
    }
}
