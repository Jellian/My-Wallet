package com.kotlin.mywallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kotlin.mywallet.databinding.FragmentProfilePictureBinding

class ProfilePictureFragment : Fragment() {

    private lateinit var binding: FragmentProfilePictureBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_profile_picture, container, false)

        binding.fragment = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewProfilePicture.setImageURI(arguments?.getString("URI")?.toUri())
    }

    fun onClickPicture(){
        findNavController().navigate(R.id.action_profilePictureFragment_to_homeFragment)
    }

}