package com.kotlin.mywallet.data

import androidx.room.*
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.Charge
import com.kotlin.mywallet.data.entities.User
import com.kotlin.mywallet.data.entities.relations.AccountWithCharges
import com.kotlin.mywallet.data.entities.relations.UserWithAccounts

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    /*
    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>
    */

    @Query("SELECT * FROM user WHERE userName LIKE :username")
    fun findByName(username: String): User

    @Query("SELECT * FROM user WHERE email = :email AND password = :passwd")
    fun findByEmailAndPassword(email: String, passwd: String): User

    @Query("SELECT * FROM user WHERE userName = :userName AND password = :passwd")
    fun findByUsernameAndPassword(userName: String, passwd: String): User

    @Query("SELECT EXISTS (SELECT * FROM user WHERE userName = :userName AND password = :password)")
    fun checkIfExistsUsernameAndPassword(userName: String, password: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM user WHERE email = :userEmail AND password = :password)")
    fun checkIfExistsEmailAndPassword(userEmail: String, password: String): Boolean

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)


    // THIS IS FOR RELATION USER - ACCOUNTS

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: Account)

    @Transaction
    @Query("SELECT * FROM user WHERE userName = :userName")
    suspend fun getUserWithAccounts(userName: String): List<UserWithAccounts>


    // THIS IS FOR RELATION ACCOUNT - TRANSACTIONS

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharge(charge: Charge)

    @Transaction
    @Query("SELECT * FROM account WHERE accountName = :accountName")
    suspend fun getAccountWithCharges(accountName: String): List<AccountWithCharges>


}