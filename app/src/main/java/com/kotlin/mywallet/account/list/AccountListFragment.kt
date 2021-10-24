package com.kotlin.mywallet.account.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.kotlin.mywallet.R
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.data.entities.Account
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AccountListFragment : Fragment() {

    private lateinit var adapter: AccountRecyclerAdapter
    private var listener: (Account) -> Unit ={}
    private lateinit var viewModel: AccountListViewModel


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Infla el layout para este Fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        viewModel = AccountListViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository
        )

        val parentActivity = activity as AccountListActivity
        val appBar = view.findViewById<Toolbar>(R.id.toolbar_listAccounts_AppBar)
        parentActivity.setSupportActionBar(appBar)

        appBar.setNavigationOnClickListener { parentActivity.finishActivity() }

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        setupAccountList()
    }

    private fun setupAccountList(){

        val parentActivity = activity as AccountListActivity?

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute {

            val accounts = viewModel.getAccountListByUser(parentActivity?.getUsername().toString())

            Handler(Looper.getMainLooper()).post{
                if(accounts.isNullOrEmpty()){
                    parentActivity?.showDialog("Ups...","No hay nada que mostrar. \nPodrías considerar: \"Agregar una cuenta\".")
                }
                else{
                    adapter = AccountRecyclerAdapter(requireContext(), accounts.toMutableList(), listener )
                    recyclerAccount.adapter = adapter
                }
            }
        }

    }

    fun setListener(l: (Account) -> Unit){
        listener = l
    }

}