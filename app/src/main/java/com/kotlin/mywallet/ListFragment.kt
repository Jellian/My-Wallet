package com.kotlin.mywallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.mywallet.finance.Cuenta
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var mAdapter: RecyclerAdapter
    private var listener: (Cuenta) -> Unit ={}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // infla el layout para este Fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    // configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView() {

        // obtenemos la activity padre
        val parentAct = activity as ListActivity?

        // indicamos que tiene un tamaño fijo
        recycler.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        recycler.layoutManager = LinearLayoutManager(activity)
        // verificamos que la activity padre no sea nulla,
        // despues obtenemos la lista de cuentas (verificando que no sean nulas tambien) y despues...
        // seteamos el Adapter
        mAdapter = parentAct?.getAccounts()?.let { RecyclerAdapter( activity!!, it, listener) }!!
        //asignando el Adapter al RecyclerView
        recycler.adapter = mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

    fun setListener(l: (Cuenta) -> Unit){
        listener = l
    }

}