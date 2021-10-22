package com.kotlin.mywallet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.mywallet.data.UserDatabase
import kotlinx.android.synthetic.main.fragment_chargelist.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChargeListFragment : Fragment() {

    private lateinit var mAdapter : RecyclerAdapter2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // infla el layout para este Fragment
        return inflater.inflate(R.layout.fragment_chargelist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView() {

        val parentActivity = activity as DetailActivity?

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(
            Runnable {

                val chargeList = UserDatabase.getInstance(requireContext())
                    ?.userDao
                    ?.findChargesByUserAndAccount(
                        parentActivity?.getUsername().toString(),
                        parentActivity?.getAccountName().toString()) as MutableList

                Handler(Looper.getMainLooper()).post(Runnable {

                    if(chargeList.isNotEmpty()){
                    // indicamos que tiene un tamaño fijo
                    recycler2.setHasFixedSize(true)
                    // indicamos el tipo de layoutManager
                    recycler2.layoutManager = LinearLayoutManager(activity)
                    //seteando el Adapter
                    mAdapter = RecyclerAdapter2(requireActivity(), chargeList)
                    //asignando el Adapter al RecyclerView
                    recycler2.adapter = mAdapter
                    }
                })
            })
    }
}

