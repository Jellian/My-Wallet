package com.kotlin.mywallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kotlin.mywallet.finance.Cuenta

class DetailActivity : AppCompatActivity() {

    private lateinit var accountNameTextView: TextView
    private lateinit var totalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val account = intent.getParcelableExtra<Cuenta>(ListActivity.ACCOUNT)

        accountNameTextView = findViewById(R.id.textView_detail_accountName)
        totalTextView = findViewById(R.id.textView_detail_totalAmount)

        accountNameTextView.text = account?.getName() ?: ""
        totalTextView.text = account?.getTotalAmount().toString()
    }
}