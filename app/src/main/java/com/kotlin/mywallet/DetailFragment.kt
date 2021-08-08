package com.kotlin.mywallet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.mywallet.finance.Cuenta

class DetailFragment : Fragment() {

    private lateinit var accountNameTextView: TextView
    private lateinit var totalTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val parentActivity = activity as DetailActivity?
        val account = parentActivity?.getAccount()

        accountNameTextView = view.findViewById(R.id.textView_detail_accountName)
        totalTextView = view.findViewById(R.id.textView_detail_totalAmount)

        accountNameTextView.text = account?.getName() ?: ""
        totalTextView.text = account?.getTotalAmount().toString()

        return view
    }
}