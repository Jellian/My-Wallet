package com.kotlin.mywallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kotlin.mywallet.finance.Cuenta

class DetailActivity : AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var total: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val product = intent.getParcelableExtra<Cuenta>("Cuenta")

        name = findViewById(R.id.tvAccountDetailName)
        total = findViewById(R.id.tvTotalAmount)

        name.text = product?.getName() ?: ""
        total.text = product?.getTotalAmount().toString()
    }
}