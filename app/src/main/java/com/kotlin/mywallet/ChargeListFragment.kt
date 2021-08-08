package com.kotlin.mywallet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mywallet.finance.Cargo
import com.kotlin.mywallet.finance.Cuenta
import kotlinx.android.synthetic.main.fragment_chargelist.*

class ChargeListFragment : Fragment() {

    private lateinit var mAdapter : RecyclerAdapter2

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            // infla el layout para este Fragment
            return inflater.inflate(R.layout.fragment_chargelist, container, false)
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView(){

        val parentActivity = activity as DetailActivity?

        val chargeList = parentActivity?.getChargesList()

        // indicamos que tiene un tamaño fijo
        recycler2.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        recycler2.layoutManager = LinearLayoutManager(activity)
        //seteando el Adapter
        mAdapter = parentActivity?.getChargesList()?.let { RecyclerAdapter2(activity!!, it) }!!
        //asignando el Adapter al RecyclerView
        recycler2.adapter = mAdapter
    }


}