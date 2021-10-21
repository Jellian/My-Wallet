package com.kotlin.mywallet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.finance.Cuenta
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ListFragment : Fragment() {

    private lateinit var mAdapter: RecyclerAdapter
    private var listener: (Account) -> Unit ={}

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Infla el layout para este Fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val parentActivity = activity as ListActivity
        val appBar = view.findViewById<Toolbar>(R.id.toolbar_listAccounts_AppBar)
        parentActivity.setSupportActionBar(appBar)

        appBar.setNavigationOnClickListener(View.OnClickListener {
            parentActivity.finishActivity()
        })

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

    // Configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView() {

        // Obtenemos la activity padre
        val parentActivity = activity as ListActivity?

        // Obtenemos las cuentas existentes
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(
            Runnable {

                val accountList = UserDatabase.getInstance(requireContext())
                    ?.userDao
                    ?.findAccountsByUser(parentActivity?.getUsername().toString()) as MutableList

                Handler(Looper.getMainLooper()).post(Runnable {

                    if(accountList.isNullOrEmpty()){
                        parentActivity?.showDialog("Ups...","No hay nada que mostrar. \nPodrías considerar: \"Agregar una cuenta\".")
                    }
                    else{
                        // Indicamos que tiene un tamaño fijo
                        recycler.setHasFixedSize(true)
                        // Indicamos el tipo de layoutManager
                        recycler.layoutManager = LinearLayoutManager(activity)
                        // Verificamos que la activity padre no sea nulla,
                        // Despues obtenemos la lista de cuentas (verificando que no sean nulas tambien) y despues...
                        // Seteamos el Adapter
                        mAdapter = RecyclerAdapter(requireActivity(), accountList, listener)
                        // Asignando el Adapter al RecyclerView
                        recycler.adapter = mAdapter
                    }
                })
            })

    }

    fun setListener(l: (Account) -> Unit){
        listener = l
    }

}