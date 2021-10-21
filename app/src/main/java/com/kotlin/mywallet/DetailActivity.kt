package com.kotlin.mywallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kotlin.mywallet.finance.Cargo
import com.kotlin.mywallet.finance.Cuenta

class DetailActivity : AppCompatActivity() {

    private lateinit var username :String
    private lateinit var accountName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        username = intent.getStringExtra(ListActivity.USERNAME).toString()
        accountName = intent.getStringExtra(ListActivity.ACCOUNT).toString()

    }

    fun getUsername(): String { return username }

    fun getAccountName(): String {  return accountName }

}



