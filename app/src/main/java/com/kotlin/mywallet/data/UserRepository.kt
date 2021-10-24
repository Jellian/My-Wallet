package com.kotlin.mywallet.data

import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.Charge
import com.kotlin.mywallet.data.entities.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository( private val userDao: UserDao, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO){

    //-------------------------------------------------------------//
    //---------------------- USERS --------------------------------//
    //-------------------------------------------------------------//

    fun getUsers() : List<User> {
        return userDao.getAllUsers()
    }

    suspend fun insertUser(user: User) = withContext(ioDispatcher){
        return@withContext userDao.insertUser(user)
    }

    suspend fun populateUsers(users: List<User>) = withContext(ioDispatcher){
        return@withContext userDao.insertAllUsers(users)
    }

    fun getUserByEmailAndPassword(email: String, password: String): User?{
        return userDao.getUserByEmailAndPassword(email, password)
    }

    fun getUserByNameAndPassword(username: String, password: String): User?{
        return userDao.getUserByNameAndPassword(username, password)
    }

    //-------------------------------------------------------------//
    //---------------------- ACCOUNTS -----------------------------//
    //-------------------------------------------------------------//

    suspend fun insertAccount(account: Account) = withContext(ioDispatcher){
        return@withContext userDao.insertAccount(account)
    }

    fun getAccountsByUser(username: String): List<Account>{
        return userDao.getAccountsByUser(username)
    }

    fun getAccountByNameAndUser(accountName: String, username: String): Account{
        return userDao.getAccountByNameAndUser(accountName, username)
    }

    suspend fun populateAccounts(accounts: List<Account>) = withContext(ioDispatcher){
        return@withContext userDao.insertAllAccounts(accounts)
    }

    fun getAccountNamesByUser(username: String): List<String>{
        return userDao.getAccountNamesByUser(username)
    }

    //-------------------------------------------------------------//
    //---------------------- CHARGES ------------------------------//
    //-------------------------------------------------------------//

    suspend fun insertCharge(charge: Charge) = withContext(ioDispatcher){
        return@withContext userDao.insertCharge(charge)
    }

    fun getChargesByUserAndAccount(username: String, accountName: String): List<Charge>{
        return userDao.getChargesByUserAndAccount(username, accountName)
    }

    suspend fun populateCharges(charges: List<Charge>) = withContext(ioDispatcher){
        return@withContext userDao.insertAllCharges(charges)
    }




}