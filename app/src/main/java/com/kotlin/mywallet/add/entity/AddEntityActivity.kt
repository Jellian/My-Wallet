package com.kotlin.mywallet.add.entity

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.mywallet.R
import com.kotlin.mywallet.home.HomeActivity
import com.kotlin.mywallet.login.MainActivity


class AddEntityActivity : AppCompatActivity() {

    private var edit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entity)

        edit = intent.getIntExtra(MainActivity.EDIT, 0)

        if(intent.getStringExtra(MainActivity.ENTITY) == "Account") {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.add_account_fragment, AddAccountFragment())
            transaction.commit()
        }
        else if (intent.getStringExtra(MainActivity.ENTITY) == "Charge") {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.add_charge_fragment, AddChargeFragment())
            transaction.commit()
        }
    }

    fun showDialog(title:String, message:String){
        AlertDialog.Builder( this ).setTitle(title).setMessage(message).setPositiveButton("OK") { _, _ -> }.create().show()
    }

    fun isEditMode(): Boolean {
        return edit == 1
    }


}