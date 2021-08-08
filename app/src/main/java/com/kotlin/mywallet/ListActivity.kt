package com.kotlin.mywallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kotlin.mywallet.finance.Cuenta
import java.lang.Exception

class ListActivity : AppCompatActivity() {

    companion object {
        const val ACCOUNT = "ACCOUNT"
        const val ACCOUNT_LIST = "ACCOUNT_LIST"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val listFragment = supportFragmentManager.findFragmentById(R.id.fragmentList) as ListFragment

        listFragment.setListener{
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(ACCOUNT, it)
                startActivity(intent)
        }

    }

    fun getAccounts(): MutableList<Cuenta>{
            // Obtenemos las cuentas existentes
            val accountList = intent.getSerializableExtra(ACCOUNT_LIST) as? ArrayList<*> //Lista de nombres de las cuentas

            val accountsToShow = mutableListOf<Cuenta>()
            var account: Cuenta?

            if (accountList?.none { it.toString().isEmpty() } == true) {
                accountList.forEach {
                    account = intent.getParcelableExtra<Cuenta>(it.toString())
                    if (account != null) {
                        accountsToShow.add(account!!)
                    }
                }
            }
            return accountsToShow
    }
}