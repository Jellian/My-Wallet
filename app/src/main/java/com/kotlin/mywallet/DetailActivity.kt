package com.kotlin.mywallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kotlin.mywallet.finance.Cargo
import com.kotlin.mywallet.finance.Cuenta

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

    }

    fun getAccount(): Cuenta? {
        return intent.getParcelableExtra<Cuenta>(ListActivity.ACCOUNT)
    }

    fun getChargesList(): MutableList<Cargo>? {
        val account = intent.getParcelableExtra<Cuenta>(ListActivity.ACCOUNT)
        return account?.getCharges()
    }
}



