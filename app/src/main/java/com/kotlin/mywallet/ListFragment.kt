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

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Infla el layout para este Fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

    // Configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView() {

        // Obtenemos la activity padre
        val parentActivity = activity as ListActivity?

        // Indicamos que tiene un tamaño fijo
        recycler.setHasFixedSize(true)
        // Indicamos el tipo de layoutManager
        recycler.layoutManager = LinearLayoutManager(activity)
        // Verificamos que la activity padre no sea nulla,
        // Despues obtenemos la lista de cuentas (verificando que no sean nulas tambien) y despues...
        // Seteamos el Adapter
        mAdapter = parentActivity?.getAccounts()?.let { RecyclerAdapter( activity!!, it, listener) }!!
        // Asignando el Adapter al RecyclerView
        recycler.adapter = mAdapter
    }

    fun setListener(l: (Cuenta) -> Unit){
        listener = l
    }

}