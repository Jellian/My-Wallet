package com.kotlin.mywallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mywallet.finance.Cuenta

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val listFragment = supportFragmentManager.findFragmentById(R.id.fragmentList) as ListFragment

        listFragment.setListener{
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("Cuenta",it)
            startActivity(intent)
        }

    }

    fun getAccounts(): MutableList<Cuenta>{
            // obtenemos las cuentas existentes
            val accountList =
                intent.getSerializableExtra("accountsList") as? ArrayList<*> //Lista de nombres de las cuentas

            val accountsToShow = mutableListOf<Cuenta>()

            if (accountList?.none { it.toString().isEmpty() } == true) {
                accountList.forEach {
                    val c = intent.getParcelableExtra<Cuenta>(it.toString())
                    if (c != null) {
                        accountsToShow.add(c)
                    }
                }
            }
            return accountsToShow
    }
}