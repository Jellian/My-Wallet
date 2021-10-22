package com.kotlin.mywallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
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

        //val parentActivity = activity as ListActivity
        val appBar = findViewById<Toolbar>(R.id.toolbar_detail_AppBar)
        setSupportActionBar(appBar)

        appBar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
    }

    fun getUsername(): String { return username }

    fun getAccountName(): String {  return accountName }

}



