package com.kotlin.mywallet.personal

import android.widget.Toast
import com.kotlin.mywallet.finance.Cuenta
import com.kotlin.mywallet.finance.Egreso
import com.kotlin.mywallet.finance.Ingreso

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

    fun addExpense(cuenta:String?="", cargo: Egreso?){
        cuentas.forEach {
            if(it.getName()== cuenta){
                it.addExpense(cargo)

                return
            }
        }
    }

    fun addIncome(cuenta:String?="", cargo: Ingreso?){
        cuentas.forEach {
            if(it.getName() == cuenta){
                it.addIncome(cargo)
                return
            }
        }
    }

    fun getAccounts(): MutableList<Cuenta>{
        return this.cuentas
    }
}