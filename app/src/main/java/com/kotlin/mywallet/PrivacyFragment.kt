package com.kotlin.mywallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kotlin.mywallet.databinding.FragmentAboutOfBinding
import com.kotlin.mywallet.databinding.FragmentPrivacyBinding
import com.kotlin.mywallet.login.MainActivity

class PrivacyFragment : Fragment() {

    private lateinit var binding: FragmentPrivacyBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPrivacyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageBedu.setOnClickListener {
            findNavController().navigate(R.id.action_privacyFragment_to_homeFragment)
        }
        binding.imageSantander.setOnClickListener {
            findNavController().navigate(R.id.action_privacyFragment_to_homeFragment)
        }
    }
}

