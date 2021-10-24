package com.kotlin.mywallet.account.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.User
import kotlinx.coroutines.launch

class AccountListViewModel(private val userRepository: UserRepository): ViewModel() {

    private var accounts: List<Account> = listOf()

    fun getAccountListByUser(username: String): List<Account>{
        accounts = userRepository.getAccountsByUser(username)
        return accounts
    }

    init{
        //prepopulateUsers()
        //prepopulateAccounts()
    }

    private fun prepopulateAccounts(){
        val accounts = listOf(
            Account(accountName = "Principal", initialAmount = 1025.2f, username = "Peter Parker"),
            Account(accountName = "Mi banco 1", initialAmount =  154.52f, username = "Drake"),
            Account(accountName = "Ahorros", initialAmount =  450.48f, username = "David Uppen"),
            Account(accountName = "Efectivo", initialAmount = 250f, username = "Dua Lipa"),
            Account(accountName = "Préstamo", initialAmount =  1500f, username = "David Uppen"),
            Account(accountName = "Cochinito", initialAmount =  458.5f, username = "Dua Lipa"),
            Account(accountName = "Rentas", initialAmount =  15000f, username = "Peter Parker"),
            Account(accountName = "Segunda", initialAmount =  4850f, username = "Peter Parker"),
            Account(accountName = "Inversión", initialAmount =  40000f, username = "Galileo Galilei"),
            Account(accountName = "Banco Nacional", initialAmount =  45000f, username = "Travis Scott"),
            Account(accountName = "Nómina", initialAmount =  15000f, username = "Enrique Peña"),
            Account(accountName = "Tarjeta metro", initialAmount =  75f, username = "Andrés Manuel"),
            Account(accountName = "Ahorros", initialAmount =  100000f, username = "Enrique Peña"),
            Account(accountName = "Ahorro Auto", initialAmount =  105000f, username = "Oswaldo Sanchéz"),
            Account(accountName = "Tarjeta Gas", initialAmount =  750f, username = "Travis Scott"),
            Account(accountName = "Caja", initialAmount =  805f, username = "Peter Parker"),
            Account(accountName = "Vales", initialAmount =  2750f, username = "Drake"),
            Account(accountName = "Dividendos", initialAmount =  2500f, username = "David Uppen"),
            Account(accountName = "Ahorros", initialAmount =  75000f, username = "Drake"),
            Account(accountName = "Tarjeta HeyBanco", initialAmount =  42000f, username = "Andrés Manuel"),
            Account(accountName = "Vacaciones", initialAmount =  6080f, username = "Dua Lipa")
        )

        viewModelScope.launch {
            userRepository.populateAccounts(accounts)
        }

    }


    private fun prepopulateUsers(){
        val users = listOf(
            User(username= "Peter Parker", email = "Peter_Parker@gmail.com", password = "SpiderverseConfirmado"),
            User(username= "Drake", email = "Drake_Drake@outlook.com", password = "OneDance2016"),
            User(username= "David Uppen", email = "David_Uppen@gmail.com", password = "Dave12345678"),
            User(username= "Dua Lipa", email = "Dua_Lipa@outlook.com", password = "betheoneIDGAF"),
            User(username= "Travis Scott", email = "Travis_Scott@gmail.com", password = "TraviesoScott:v"),
            User(username= "Galileo Galilei", email = "Galileo_Galilei@hotmail.com", password = "GalileoGalileo"),
            User(username= "Enrique Peña", email = "Enrique_pena@gmail.com", password = "5...no,menos...10"),
            User(username= "Andrés Manuel", email = "AMLO2018@outlook.com", password = "AMLO2018:v"),
            User(username= "Oswaldo Sanchéz", email = "OswalditoUwU@gmail.com", password = "JuegaLimpioSienteTuLiga")
        )

        viewModelScope.launch {
            userRepository.populateUsers(users)
        }

    }

}