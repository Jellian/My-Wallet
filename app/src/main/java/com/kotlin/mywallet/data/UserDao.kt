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
    fun insertAccount(account: Account)

    @Transaction
    @Query("SELECT * FROM user WHERE userName = :userName")
    fun getUserWithAccounts(userName: String): List<UserWithAccounts>

    @Transaction
    @Query("SELECT accountName FROM account WHERE userName = :userName")
    fun findAccountNamesByUser(userName: String): List<String>

    @Transaction
    @Query("SELECT * FROM account WHERE userName = :userName")
    fun findAccountsByUser(userName: String): List<Account>

    @Transaction
    @Query("SELECT * FROM account WHERE userName = :userName AND accountName = :accountName")
    fun findAccountByUserAndName(userName: String, accountName: String): Account

    // THIS IS FOR RELATION ACCOUNT - CHARGES

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharge(vararg charge: Charge)

    @Transaction
    @Query("SELECT * FROM account WHERE accountName = :accountName")
    suspend fun getAccountWithCharges(accountName: String): List<AccountWithCharges>

    @Transaction
    @Query("SELECT * FROM charge WHERE userName = :userName AND accountName= :accountName")
    fun findChargesByUserAndAccount(userName: String, accountName: String): List<Charge>


}