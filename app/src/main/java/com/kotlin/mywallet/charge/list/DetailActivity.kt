package com.kotlin.mywallet.charge.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.kotlin.mywallet.R
import com.kotlin.mywallet.account.list.AccountListActivity
import com.kotlin.mywallet.login.MainActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var username :String
    private lateinit var accountName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        username = intent.getStringExtra(MainActivity.USERNAME).toString()
        accountName = intent.getStringExtra(MainActivity.ACCOUNT).toString()

        //val parentActivity = activity as ListActivity
        val appBar = findViewById<Toolbar>(R.id.toolbar_detail_AppBar)
        setSupportActionBar(appBar)

        appBar.setNavigationOnClickListener { finish() }
    }

    fun getUsername(): String { return username }

    fun getAccountName(): String {  return accountName }

}



