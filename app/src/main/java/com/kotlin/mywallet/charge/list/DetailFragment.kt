package com.kotlin.mywallet.charge.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.mywallet.R
import com.kotlin.mywallet.account.list.AccountListActivity
import com.kotlin.mywallet.account.list.AccountListViewModel
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.data.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailFragment : Fragment() {

    private lateinit var accountNameTextView: TextView
    private lateinit var totalTextView: TextView

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        viewModel = DetailViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository
        )

        setUpView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountNameTextView = view.findViewById(R.id.textView_detail_accountName)
        totalTextView = view.findViewById(R.id.textView_detail_totalAmount)

    }

    private fun setUpView() {

        val parentActivity = activity as DetailActivity?
        val accountName = parentActivity?.intent?.getStringExtra(AccountListActivity.ACCOUNT).toString()
        val username = parentActivity?.intent?.getStringExtra(AccountListActivity.USERNAME).toString()

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {

            val account = viewModel.getAccountByNameAndUser(accountName, username)

            Handler(Looper.getMainLooper()).post {
                accountNameTextView.text = account.accountName
                totalTextView.text = account.totalAmount.toString()
            }
        }
    }
}