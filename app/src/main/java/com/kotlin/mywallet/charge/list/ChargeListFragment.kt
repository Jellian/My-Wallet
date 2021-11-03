package com.kotlin.mywallet.charge.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kotlin.mywallet.R
import com.kotlin.mywallet.add.entity.AddEntityActivity
import com.kotlin.mywallet.application.WalletApplication
import com.kotlin.mywallet.databinding.FragmentChargelistBinding
import com.kotlin.mywallet.login.MainActivity
import kotlinx.android.synthetic.main.fragment_chargelist.*

class ChargeListFragment : Fragment() {

    private lateinit var adapter : ChargeAdapter
    private lateinit var viewModel: ChargeListViewModel
    private lateinit var binding: FragmentChargelistBinding

    private lateinit var parentActivity: DetailActivity
    private lateinit var username: String
    private lateinit var accountName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chargelist, container, false)

        parentActivity = activity as DetailActivity

        username = parentActivity.getUsername()
        accountName = parentActivity.getAccountName()

        viewModel = ChargeListViewModel(
            (requireContext().applicationContext as WalletApplication).userRepository, username, accountName
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEditCharge()
        setupChargeList()
    }

    private fun setupChargeList() {

        adapter = ChargeAdapter(requireContext(), viewModel)

        viewModel.charges.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        recyclerCharge.adapter = adapter

    }

    private fun setupEditCharge(){
        with(viewModel) {
            eventEditChargeId.observe(viewLifecycleOwner, {
                if(eventEditChargeId.value != null){
                    editCharge(eventEditChargeId.value!!)
                }
            })
        }
    }

    private fun editCharge(chargeId: Int) {
        val intent = Intent(context, AddEntityActivity::class.java)
        intent.putExtra(MainActivity.ID, chargeId )
        intent.putExtra(MainActivity.ENTITY, "Charge")
        intent.putExtra(MainActivity.EDIT, 1)
        startActivity(intent)
    }
}

