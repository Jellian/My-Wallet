package com.kotlin.mywallet.utils

class Categories() {

    companion object categoryList {

        val incomeOptions = listOf(
            "Depósito", "Salario", "Inversión","Bono",
            "Ahorro", "Renta", "Ventas",
        )
        val expendOptions = listOf(
            "Transporte",  "Comida", "Entretenimiento", "Casa",
            "Ropa",  "Salud",  "Auto", "Restaurante"
        )
    }
}