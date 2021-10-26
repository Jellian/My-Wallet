package com.kotlin.mywallet.account.list

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.mywallet.charge.list.DetailActivity
import com.kotlin.mywallet.R
import com.kotlin.mywallet.home.HomeActivity
import com.kotlin.mywallet.login.MainActivity

class AccountListActivity : AppCompatActivity() {

    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val listFragment = supportFragmentManager.findFragmentById(R.id.fragmentList) as AccountListFragment

        username = intent.getStringExtra(MainActivity.USER_NAME).toString()

        listFragment.setListener{
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(MainActivity.USERNAME, username)
                intent.putExtra(MainActivity.ACCOUNT, it.accountName)
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