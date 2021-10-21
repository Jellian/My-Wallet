package com.kotlin.mywallet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.finance.Cuenta
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailFragment : Fragment() {

    private lateinit var accountNameTextView: TextView
    private lateinit var totalTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        accountNameTextView = view.findViewById(R.id.textView_detail_accountName)
        totalTextView = view.findViewById(R.id.textView_detail_totalAmount)

        setUpView()

        return view
    }

    private fun setUpView() {

        val parentActivity = activity as DetailActivity?
        val accountName = parentActivity?.intent?.getStringExtra(ListActivity.ACCOUNT).toString()
        val userName = parentActivity?.intent?.getStringExtra(ListActivity.USERNAME).toString()

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(
            Runnable {

                val account = UserDatabase.getInstance(requireContext())
                    ?.userDao
                    ?.findAccountByUserAndName(userName, accountName)

                Handler(Looper.getMainLooper()).post(Runnable {
                    accountNameTextView.text = account?.accountName
                    totalTextView.text = account?.totalAmount.toString()
                })
            })
    }
}