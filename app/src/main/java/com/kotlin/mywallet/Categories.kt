package com.kotlin.mywallet

class Categories() {

    companion object categoryList {

        val incomeOptions = listOf(
            "Depósito", "Salario", "Inversión","Bono",
            "Ahorro", "Renta", "Venta",
        )

        val expendOptions = listOf(
            "Transporte",  "Comida", "Entretenimiento", "Casa",
            "Ropa",  "Salud",  "Auto", "Restaurante"
        )

    }
}