package com.kotlin.mywallet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.kotlin.mywallet.databinding.FragmentGoalBinding
import com.kotlin.mywallet.home.HomeActivity
import com.kotlin.mywallet.login.MainActivity
import kotlinx.android.synthetic.main.activity_home.*

class GoalFragment : Fragment() {

    lateinit var preferences: SharedPreferences
    lateinit var binding: FragmentGoalBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        binding = FragmentGoalBinding.inflate(layoutInflater)

        preferences = activity?.getSharedPreferences(HomeActivity.PREFS_NAME, Context.MODE_PRIVATE) as SharedPreferences

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBar = binding.toolbarGoalFragmentAppBar
        (activity as AppCompatActivity).setSupportActionBar(appBar)

        with(binding){

            etGoal.setText(loadPreferences().toString())

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

    private  fun loadPreferences(): Float{
        val goal = preferences.getFloat(HomeActivity.GOAL,0f)
        return goal
    }

    private  fun setGoal(goal: Float){

        if (goal > 0){
            preferences.edit().putFloat(HomeActivity.GOAL,goal).apply()
            Toast.makeText(context,"Meta agregada", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_goalFragment_to_homeFragment)
        }else
            Toast.makeText(context,"Ingresa una meta mayor a cero", Toast.LENGTH_SHORT).show()
    }

}
