package com.kotlin.mywallet.charge.list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.kotlin.mywallet.R
import com.kotlin.mywallet.account.list.AccountListActivity
import com.kotlin.mywallet.account.list.AccountListViewModel
import com.kotlin.mywallet.add.entity.AddEntityActivity
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.databinding.FragmentDetailBinding
import com.kotlin.mywallet.databinding.FragmentHomeBinding
import com.kotlin.mywallet.login.MainActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var parentActivity: DetailActivity

    private lateinit var viewModel: DetailViewModel
    private lateinit var accountName: String
    private lateinit var username: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this

        parentActivity = activity as DetailActivity

        username = parentActivity.getUsername()
        accountName = parentActivity.getAccountName()

        viewModel = DetailViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository, username, accountName
        )

        setUpView()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    private fun setUpView() {

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {

            val account = viewModel.getAccountByNameAndUser(accountName, username)

            Handler(Looper.getMainLooper()).post {
                binding.textViewDetailAccountName.text = account.accountName
            }
        }

        binding.buttonDetailAddIncome.setOnClickListener( prepareCharge() )
        binding.buttonDetailAddExpense.setOnClickListener( prepareCharge() )

    }

    private fun prepareCharge() = View.OnClickListener { view ->
        val intent = Intent(context, AddEntityActivity::class.java)
        // Boton que ha llamado a la funcion
        when (view.id) {
            R.id.button_detail_addIncome -> intent.putExtra(MainActivity.TYPE, +1)
            else -> intent.putExtra(MainActivity.TYPE, -1)
        }
        intent.putExtra(MainActivity.USER_NAME, username)
        intent.putExtra(MainActivity.ENTITY, "Charge")
        intent.putExtra(MainActivity.ACCOUNT, accountName)

        startActivity(intent)
    }
}