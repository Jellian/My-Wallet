package com.kotlin.mywallet.personal

import com.kotlin.mywallet.finance.Cuenta

class Usuario(private var nombre: String = "") {

    private var cuentas = mutableListOf<Cuenta>()
    //private var allInfo = AllInfo()

    fun addAccount(name:String, initAmt: Float){
        val newId = cuentas.size
        val newAccount = Cuenta(newId, name, initAmt)

        cuentas.add(newAccount)
    }

    fun getAccountNames(): ArrayList<String> {
        val namesList = ArrayList<String>()
        cuentas.forEach{namesList.add(it.getName())}

        return namesList
    }


}