package com.kotlin.mywallet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kotlin.mywallet.databinding.ActivityGoalBinding

class GoalActivity : AppCompatActivity() {
    lateinit var preferences: SharedPreferences
    lateinit var binding: ActivityGoalBinding
    lateinit var userName:String
    lateinit var email:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userName = intent.getStringExtra(MainActivity.USER_NAME)!!
        email = intent.getStringExtra(MainActivity.USER_EMAIL)!!
        preferences = getSharedPreferences(HomeActivity.PREFS_NAME, Context.MODE_PRIVATE)
        with(binding){
            setSupportActionBar(toolbarAddAccountAppBar)
            toolbarAddAccountAppBar.setNavigationOnClickListener {
                setResult(Activity.RESULT_CANCELED)
            }
            etGoal.setText(loadPreferences().toString())
            btnGoalAdd.setOnClickListener{
                val goal = etGoal.text.toString().toFloat()
                if (goal > 0){
                    setGoal(goal)
                    Toast.makeText(applicationContext,"Meta agregada", Toast.LENGTH_SHORT).show()
                    goBack()
                }else{
                    Toast.makeText(applicationContext,"Ingresa una meta mayor a cero", Toast.LENGTH_SHORT).show()
                }

            }
            btnGoalBack.setOnClickListener {
                goBack()
            }
        }
    }
    private  fun loadPreferences(): Float{
        val goal = preferences.getFloat(HomeActivity.GOAL,0f)
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> $goal")
        return goal
    }
    private  fun setGoal(goal: Float){
        preferences.edit().putFloat(HomeActivity.GOAL,goal).apply()
    }
    private fun goBack(){
        val i = Intent(this, com.kotlin.mywallet.HomeActivity::class.java)
        intent.putExtra(MainActivity.USER_NAME, userName)
        intent.putExtra(MainActivity.USER_EMAIL, email)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}