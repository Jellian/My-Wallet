package com.kotlin.mywallet.add.entity

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.mywallet.R
import com.kotlin.mywallet.home.HomeActivity


class AddEntityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entity)

        if(intent.getStringExtra(HomeActivity.ENTITY) == "Account") {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.add_account_fragment, AddAccountFragment())
            transaction.commit()
        }
        else if (intent.getStringExtra(HomeActivity.ENTITY) == "Charge") {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.add_charge_fragment, AddChargeFragment())
            transaction.commit()
        }
    }

    fun showDialog(title:String, message:String){
        AlertDialog.Builder( this ).setTitle(title).setMessage(message).setPositiveButton("OK") { _, _ -> }.create().show()
    }

    fun finishActivity() {
        finish()
    }
}