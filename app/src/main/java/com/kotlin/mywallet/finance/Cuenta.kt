package com.kotlin.mywallet.finance

class Cuenta(private var id: Int, private var name: String, private val initialAmount:Float = 0.0f) {
    private var currency = "MXN"
    private var totalAmount = 0f
    private var expenses = mutableListOf<Egreso>()
    private var incomes = mutableListOf<Ingreso>()

    fun getName(): String{
        return name
    }



}