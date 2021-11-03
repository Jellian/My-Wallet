package com.kotlin.mywallet.account.list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kotlin.mywallet.R
import com.kotlin.mywallet.add.entity.AddEntityActivity
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.databinding.FragmentListBinding
import com.kotlin.mywallet.login.MainActivity
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AccountListFragment : Fragment() {

    private lateinit var adapter: AccountAdapter
    private var listener: (Account) -> Unit ={}
    private lateinit var viewModel: AccountListViewModel
    private lateinit var binding: FragmentListBinding

    private lateinit var parentActivity: AccountListActivity
    private lateinit var username: String

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        parentActivity = activity as AccountListActivity
        username = parentActivity.getUsername()

        viewModel = AccountListViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository, username
        )

        val appBar = binding.toolbarListAccountsAppBar
        parentActivity.setSupportActionBar(appBar)

        appBar.setNavigationOnClickListener { parentActivity.finish() }

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        setupEditAccount()
        setupAccountList()
    }

    private fun setupAccountList() {

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        var howManyAccounts = 0

        executor.execute {
            howManyAccounts = viewModel.getAccountsCountByUser(username)
        }
        Handler(Looper.getMainLooper()).post {
            if (howManyAccounts == 0)
                parentActivity.showDialog(
                    "Ups...",
                    "No hay nada que mostrar. \nPodrías considerar: \"Agregar una cuenta\"."
                )
            else {
                adapter = AccountAdapter(requireContext(), viewModel, listener)

                viewModel.accounts.observe(viewLifecycleOwner, {
                    adapter.submitList(it)
                })

                recyclerAccount.adapter = adapter
            }
        }
    }

    private fun setupEditAccount(){
        with(viewModel) {
            eventEditAccountId.observe(viewLifecycleOwner, {
                if(eventEditAccountId.value != null){
                    editAccount(eventEditAccountId.value!!)
                }
            })
        }
    }

    private fun editAccount(accountId: Int) {
        val intent = Intent(context, AddEntityActivity::class.java)
        intent.putExtra(MainActivity.ID, accountId )
        intent.putExtra(MainActivity.ENTITY, "Account")
        intent.putExtra(MainActivity.EDIT, 1)
        startActivity(intent)
    }

    fun setListener(l: (Account) -> Unit){
        listener = l
    }

}