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
    fun getAllUsers(): List<User>

    @Insert
    suspend fun insertAllUsers(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(vararg users: User)

    /*
    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>
    */

    @Query("SELECT * FROM user WHERE username LIKE :username")
    suspend fun findByName(username: String): User

    @Transaction
    @Query("SELECT * FROM user WHERE email = :email AND password = :passwd")
    fun getUserByEmailAndPassword(email: String, passwd: String): User?

    @Query("SELECT * FROM user WHERE username = :userName AND password = :passwd")
    fun getUserByNameAndPassword(userName: String, passwd: String): User?

    @Query("SELECT EXISTS (SELECT * FROM user WHERE username = :userName AND password = :password)")
    suspend fun checkIfExistsUsernameAndPassword(userName: String, password: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM user WHERE email = :userEmail AND password = :password)")
    suspend fun checkIfExistsEmailAndPassword(userEmail: String, password: String): Boolean



    @Delete
    suspend fun delete(user: User)


    // THIS IS FOR RELATION USER - ACCOUNTS

    @Insert
    suspend fun insertAllAccounts(accounts: List<Account>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(vararg account: Account)

    @Transaction
    @Query("SELECT * FROM user WHERE username = :userName")
    suspend fun getUserWithAccounts(userName: String): List<UserWithAccounts>

    @Transaction
    @Query("SELECT accountName FROM account WHERE userName = :userName")
    fun getAccountNamesByUser(userName: String): List<String>

    @Transaction
    @Query("SELECT * FROM account WHERE userName = :userName")
    fun getAccountsByUser(userName: String): List<Account>

    @Transaction
    @Query("SELECT * FROM account WHERE userName = :userName AND accountName = :accountName")
    fun getAccountByNameAndUser(accountName: String, userName: String): Account

    // THIS IS FOR RELATION ACCOUNT - CHARGES

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharge(vararg charge: Charge)

    @Transaction
    @Query("SELECT * FROM account WHERE accountName = :accountName")
    suspend fun getAccountWithCharges(accountName: String): List<AccountWithCharges>

    @Transaction
    @Query("SELECT * FROM charge WHERE userName = :userName AND accountName= :accountName")
    fun getChargesByUserAndAccount(userName: String, accountName: String): List<Charge>

    @Insert
    suspend fun insertAllCharges(charges: List<Charge>)


}