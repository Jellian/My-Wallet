package com.kotlin.mywallet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.databinding.FragmentGoalBinding
import com.kotlin.mywallet.login.MainActivity
import com.kotlin.mywallet.login.SignInViewModel

class GoalFragment : Fragment() {

    private lateinit var binding: FragmentGoalBinding
    private lateinit var viewModel: GoalViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_goal, container, false)
        binding.lifecycleOwner = this

        viewModel = GoalViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository, requireContext()
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBar = binding.toolbarGoalFragmentAppBar
        (activity as AppCompatActivity).setSupportActionBar(appBar)

        with(binding){

            appBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            btnGoalAdd.setOnClickListener{
                setGoal(etGoal.text.toString().toFloat())
            }

            btnGoalBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private  fun setGoal(goal: Float){

        if (goal > 0){
            viewModel.updateActualGoalByUser(goal)
            Toast.makeText(context,"Meta agregada", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_goalFragment_to_homeFragment)
        }else
            Toast.makeText(context,"Ingresa una meta mayor a cero", Toast.LENGTH_SHORT).show()
    }

}
