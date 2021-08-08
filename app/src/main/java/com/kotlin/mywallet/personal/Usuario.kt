package com.kotlin.mywallet.personal

import com.kotlin.mywallet.finance.Cuenta
import com.kotlin.mywallet.finance.Cargo

class Usuario(private var nombre: String? = "") {

    private var cuentas = mutableListOf<Cuenta>()
    private var grandTotal = 0.0f
    //private var allInfo = AllInfo()

    fun addAccount(accountName:String, initialAmount: Float){
        val newId = cuentas.size
        val newAccount = Cuenta(newId, accountName, initialAmount)
        cuentas.add(newAccount)
        grandTotal += newAccount.getTotalAmount()
    }

    fun getAccountNames(): ArrayList<String> {
        val namesList = ArrayList<String>()
        cuentas.forEach{namesList.add(it.getName())}

        return namesList
    }

    fun addExpense(accountName: String?="", charge: Cargo?){
        cuentas.forEach {
            if(it.getName()== accountName){
                it.addExpense(charge)
                grandTotal += charge?.getAmount() ?: 0.0f
                return
            }
        }
    }

    fun addIncome(accountName: String?="", charge: Cargo?){
        cuentas.forEach {
            if(it.getName() == accountName){
                it.addIncome(charge)
                grandTotal += charge?.getAmount() ?: 0.0f
                return
            }
        }
    }

    fun getAccounts(): MutableList<Cuenta>{
        return this.cuentas
    }

    fun getGrandTotal(): Float{
        return grandTotal
    }
}