package com.kotlin.mywallet.charge.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlin.mywallet.R
import com.kotlin.mywallet.application.WalletApplication
import kotlinx.android.synthetic.main.fragment_chargelist.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChargeListFragment : Fragment() {

    private lateinit var adapter : ChargeRecyclerAdapter
    private lateinit var viewModel: ChargeListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // infla el layout para este Fragment
        val view = inflater.inflate(R.layout.fragment_chargelist, container, false)

        viewModel = ChargeListViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository
        )

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupChargeList()
    }

    private fun setupChargeList() {

        val parentActivity = activity as DetailActivity?

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute {

            val charges = viewModel.getChargeListByUserAndAccount(
                parentActivity?.getUsername().toString(),
                parentActivity?.getAccountName().toString()
            )

            Handler(Looper.getMainLooper()).post {
                if (charges.isNotEmpty()) {
                    adapter = ChargeRecyclerAdapter(requireContext(), charges.toMutableList())
                    recyclerCharge.adapter = adapter
                }
            }
        }
    }
}

