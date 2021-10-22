package com.kotlin.mywallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kotlin.mywallet.databinding.FragmentAboutOfBinding
import com.kotlin.mywallet.databinding.FragmentMainBinding
import com.kotlin.mywallet.login.MainActivity

class AboutOfFragment : Fragment() {

    private lateinit var binding: FragmentAboutOfBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutOfBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAboutGenial.setOnClickListener { findNavController().navigate(R.id.action_aboutOfFragment_to_homeFragment)  }
    }

}