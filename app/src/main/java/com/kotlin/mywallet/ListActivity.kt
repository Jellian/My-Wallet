package com.kotlin.mywallet

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ListActivity : AppCompatActivity() {

    companion object {
        const val ACCOUNT = "ACCOUNT"
        const val USERNAME = "USERNAME"
    }

    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val listFragment = supportFragmentManager.findFragmentById(R.id.fragmentList) as ListFragment

        username = intent.getStringExtra(HomeActivity.USER_NAME).toString()

        listFragment.setListener{
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(USERNAME, username)
                intent.putExtra(ACCOUNT, it.accountName)
                startActivity(intent)
        }

    }

    fun finishActivity() {
        finish()
    }

    fun getUsername(): String{
        return username
    }

    fun showDialog(title:String,message:String) {
        AlertDialog.Builder(this).setTitle(title).setMessage(message)
            .setPositiveButton("OK") { _, _ -> finish() }.create().show()
    }
}