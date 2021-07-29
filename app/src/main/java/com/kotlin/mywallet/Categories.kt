package com.kotlin.mywallet

class Categories() {

    companion object categoryList {

        val incomeOptions = listOf(
            "Deposit", "Salary", "Investment","Bonus",
            "Holiday Pay", "Rent", "Sales", "loans"
        )

        val expendOptions = listOf(
            "Transport",  "Food", "Entertainment", "Home",
            "Clothes",  "Health",  "Auto", "Restaurant"
        )

    }

}